package ke.co.hakicheck.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ke.co.hakicheck.domain.model.ClaimVerification
import ke.co.hakicheck.domain.model.EvidenceSource
import ke.co.hakicheck.domain.model.VerdictType

@Entity(tableName = "saved_claims")
data class SavedClaimEntity(
    @PrimaryKey
    val id: String,
    val claimText: String,
    val verdictString: String,
    val summary: String,
    val groundedContext: String,
    val confidenceScore: Int,
    val checkedAtMillis: Long,
    val sourceCategory: String,
    // Serialization helper: stored as joined string or serialized JSON
    val evidenceTitles: String,
    val evidenceCitations: String,
    val evidenceSnippets: String
) {
    fun toDomain(): ClaimVerification {
        val titles = evidenceTitles.split("|||").filter { it.isNotBlank() }
        val citations = evidenceCitations.split("|||").filter { it.isNotBlank() }
        val snippets = evidenceSnippets.split("|||").filter { it.isNotBlank() }

        val sources = titles.indices.map { i ->
            EvidenceSource(
                title = titles.getOrElse(i) { "Kenyan Legal Record" },
                legalCitation = citations.getOrElse(i) { "Constitution of Kenya 2010" },
                extractSnippet = snippets.getOrElse(i) { "" },
                documentType = "STATUTE"
            )
        }

        return ClaimVerification(
            id = id,
            claimText = claimText,
            verdict = VerdictType.fromString(verdictString),
            summary = summary,
            groundedContext = groundedContext,
            evidenceSources = sources,
            confidenceScore = confidenceScore,
            checkedAtMillis = checkedAtMillis,
            sourceCategory = sourceCategory,
            isSaved = true
        )
    }

    companion object {
        fun fromDomain(domain: ClaimVerification): SavedClaimEntity {
            return SavedClaimEntity(
                id = domain.id,
                claimText = domain.claimText,
                verdictString = domain.verdict.name,
                summary = domain.summary,
                groundedContext = domain.groundedContext,
                confidenceScore = domain.confidenceScore,
                checkedAtMillis = domain.checkedAtMillis,
                sourceCategory = domain.sourceCategory,
                evidenceTitles = domain.evidenceSources.joinToString("|||") { it.title },
                evidenceCitations = domain.evidenceSources.joinToString("|||") { it.legalCitation },
                evidenceSnippets = domain.evidenceSources.joinToString("|||") { it.extractSnippet }
            )
        }
    }
}
