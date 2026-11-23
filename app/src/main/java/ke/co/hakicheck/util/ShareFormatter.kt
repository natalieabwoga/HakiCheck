package ke.co.hakicheck.util

import android.content.Context
import ke.co.hakicheck.R
import ke.co.hakicheck.domain.model.ClaimVerification
import ke.co.hakicheck.domain.model.VerdictType

object ShareFormatter {

    fun formatForWhatsApp(context: Context, verification: ClaimVerification): String {
        val verdictLabel = context.getString(verification.verdict.titleRes)
        val iconEmoji = when (verification.verdict) {
            VerdictType.VERIFIED_TRUE -> "✅"
            VerdictType.MOSTLY_TRUE -> "🟢"
            VerdictType.MISLEADING -> "⚠️"
            VerdictType.VERIFIED_FALSE -> "❌"
            VerdictType.UNVERIFIED, VerdictType.CIVIC_INFO -> "ℹ️"
        }

        return buildString {
            append("🇰🇪 *HAKICHECK CIVIC FACT-CHECK*\n")
            append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n")
            append("*Analyzed Claim:*\n")
            append("“${verification.claimText}”\n\n")
            append("*Verdict:* $iconEmoji *$verdictLabel*\n\n")
            append("*Grounded Legal Summary:*\n")
            append("${verification.summary}\n\n")

            if (verification.evidenceSources.isNotEmpty()) {
                append("*Authoritative Legal Citations:*\n")
                for (source in verification.evidenceSources) {
                    append("• *${source.title}* (${source.legalCitation})\n")
                }
                append("\n")
            }

            append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
            append("Confidence: ${verification.confidenceScore}% • Grounded in Laws of Kenya\n")
            append("_Verified via HakiCheck • Strathmore University SCES_")
        }
    }
}
