package ke.co.hakicheck.domain.model

import java.io.Serializable

data class ClaimVerification(
    val id: String,
    val claimText: String,
    val verdict: VerdictType,
    val summary: String,
    val groundedContext: String,
    val evidenceSources: List<EvidenceSource>,
    val confidenceScore: Int = 95,
    val checkedAtMillis: Long = System.currentTimeMillis(),
    val sourceCategory: String = "ELECTIONS",
    var isSaved: Boolean = false
) : Serializable
