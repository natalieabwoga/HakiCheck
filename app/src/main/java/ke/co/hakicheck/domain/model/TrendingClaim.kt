package ke.co.hakicheck.domain.model

import java.io.Serializable

data class TrendingClaim(
    val id: String,
    val claimTitle: String,
    val viralContext: String,
    val verdict: VerdictType,
    val summary: String,
    val verification: ClaimVerification,
    val viralCount: Int = 1200
) : Serializable
