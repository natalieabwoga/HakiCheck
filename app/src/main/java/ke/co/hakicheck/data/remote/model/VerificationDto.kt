package ke.co.hakicheck.data.remote.model

import com.google.gson.annotations.SerializedName

data class VerificationRequestDto(
    @SerializedName("claim")
    val claim: String,
    @SerializedName("county")
    val county: String? = null,
    @SerializedName("language")
    val language: String? = "English",
    @SerializedName("include_sources")
    val includeSources: Boolean = true
)

data class EvidenceSourceDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("citation")
    val citation: String,
    @SerializedName("snippet")
    val snippet: String,
    @SerializedName("document_type")
    val documentType: String? = "STATUTE",
    @SerializedName("url")
    val url: String? = null
)

data class VerificationResponseDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("claim")
    val claim: String,
    @SerializedName("verdict")
    val verdict: String, // "FALSE", "TRUE", "MOSTLY_TRUE", "MISLEADING", "UNVERIFIED"
    @SerializedName("summary")
    val summary: String,
    @SerializedName("grounded_context")
    val groundedContext: String?,
    @SerializedName("confidence_score")
    val confidenceScore: Int = 90,
    @SerializedName("evidence_sources")
    val evidenceSources: List<EvidenceSourceDto>? = null,
    @SerializedName("category")
    val category: String? = "ELECTIONS"
)
