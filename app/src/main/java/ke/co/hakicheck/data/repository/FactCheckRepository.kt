package ke.co.hakicheck.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ke.co.hakicheck.data.local.SavedClaimDao
import ke.co.hakicheck.data.local.SavedClaimEntity
import ke.co.hakicheck.data.remote.ApiClient
import ke.co.hakicheck.data.remote.HakiCheckApiService
import ke.co.hakicheck.data.remote.model.VerificationRequestDto
import ke.co.hakicheck.domain.model.ClaimVerification
import ke.co.hakicheck.domain.model.EvidenceSource
import ke.co.hakicheck.domain.model.TrendingClaim
import ke.co.hakicheck.domain.model.VerdictType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID

class FactCheckRepository(
    private val savedClaimDao: SavedClaimDao,
    private val settingsRepository: SettingsRepository
) {

    val savedClaims: LiveData<List<ClaimVerification>> =
        savedClaimDao.getAllSavedClaims().map { entities ->
            entities.map { it.toDomain() }
        }

    fun searchSavedClaims(query: String): LiveData<List<ClaimVerification>> {
        return savedClaimDao.searchSavedClaims(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun verifyClaim(query: String): ClaimVerification = withContext(Dispatchers.IO) {
        val profile = settingsRepository.loadProfile()

        if (profile.isOfflineMode) {
            // Simulated realistic delay for AI inference in offline mode (800ms)
            delay(800)
            val result = OfflineSeedData.findOrGenerateVerification(query, profile.language)
            result.isSaved = savedClaimDao.isClaimSaved(result.id)
            return@withContext result
        }

        // Live Mode: Connect to FastAPI RAG Server using user-configured base URL
        try {
            val apiService: HakiCheckApiService = ApiClient.createService(profile.serverUrl)
            val response = apiService.verifyClaim(
                VerificationRequestDto(
                    claim = query,
                    county = profile.county,
                    language = profile.language,
                    includeSources = true
                )
            )

            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val sources = dto.evidenceSources?.map {
                    EvidenceSource(
                        title = it.title,
                        legalCitation = it.citation,
                        extractSnippet = it.snippet,
                        documentType = it.documentType ?: "STATUTE",
                        sourceUrl = it.url
                    )
                } ?: emptyList()

                val domainResult = ClaimVerification(
                    id = dto.id ?: UUID.randomUUID().toString(),
                    claimText = dto.claim,
                    verdict = VerdictType.fromString(dto.verdict),
                    summary = dto.summary,
                    groundedContext = dto.groundedContext ?: "",
                    evidenceSources = sources,
                    confidenceScore = dto.confidenceScore,
                    sourceCategory = dto.category ?: "ELECTIONS"
                )
                domainResult.isSaved = savedClaimDao.isClaimSaved(domainResult.id)
                return@withContext domainResult
            } else {
                // Fallback to offline seed corpus if server returned error
                val fallback = OfflineSeedData.findOrGenerateVerification(query, profile.language)
                fallback.isSaved = savedClaimDao.isClaimSaved(fallback.id)
                return@withContext fallback
            }
        } catch (e: Exception) {
            // Fallback to offline corpus on connection timeout / offline network
            val fallback = OfflineSeedData.findOrGenerateVerification(query, profile.language)
            fallback.isSaved = savedClaimDao.isClaimSaved(fallback.id)
            return@withContext fallback
        }
    }

    suspend fun toggleSaveClaim(verification: ClaimVerification): Boolean = withContext(Dispatchers.IO) {
        val isCurrentlySaved = savedClaimDao.isClaimSaved(verification.id)
        if (isCurrentlySaved) {
            savedClaimDao.deleteClaimById(verification.id)
            verification.isSaved = false
            return@withContext false
        } else {
            savedClaimDao.insertClaim(SavedClaimEntity.fromDomain(verification))
            verification.isSaved = true
            return@withContext true
        }
    }

    suspend fun isClaimSaved(id: String): Boolean = withContext(Dispatchers.IO) {
        savedClaimDao.isClaimSaved(id)
    }

    suspend fun clearAllSaved() = withContext(Dispatchers.IO) {
        savedClaimDao.clearAll()
    }

    fun getTrendingClaims(): List<TrendingClaim> {
        return OfflineSeedData.trendingClaims
    }
}
