package ke.co.hakicheck.domain.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ke.co.hakicheck.R

enum class VerdictType(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @ColorRes val badgeTextColor: Int,
    @DrawableRes val badgeBackground: Int,
    @DrawableRes val cardBackground: Int,
    @DrawableRes val iconRes: Int
) {
    VERIFIED_FALSE(
        titleRes = R.string.verdict_false,
        descriptionRes = R.string.verdict_desc_false,
        badgeTextColor = R.color.verdict_false_text,
        badgeBackground = R.drawable.bg_badge_verdict_false,
        cardBackground = R.drawable.bg_card_verdict_false,
        iconRes = R.drawable.ic_verdict_false
    ),
    VERIFIED_TRUE(
        titleRes = R.string.verdict_true,
        descriptionRes = R.string.verdict_desc_true,
        badgeTextColor = R.color.verdict_true_text,
        badgeBackground = R.drawable.bg_badge_verdict_true,
        cardBackground = R.drawable.bg_card_verdict_true,
        iconRes = R.drawable.ic_verdict_true
    ),
    MOSTLY_TRUE(
        titleRes = R.string.verdict_mostly_true,
        descriptionRes = R.string.verdict_desc_mostly_true,
        badgeTextColor = R.color.verdict_mostly_true_text,
        badgeBackground = R.drawable.bg_badge_verdict_mostly_true,
        cardBackground = R.drawable.bg_card_verdict_mostly_true,
        iconRes = R.drawable.ic_verdict_mostly_true
    ),
    MISLEADING(
        titleRes = R.string.verdict_misleading,
        descriptionRes = R.string.verdict_desc_misleading,
        badgeTextColor = R.color.verdict_misleading_text,
        badgeBackground = R.drawable.bg_badge_verdict_misleading,
        cardBackground = R.drawable.bg_card_verdict_misleading,
        iconRes = R.drawable.ic_verdict_misleading
    ),
    UNVERIFIED(
        titleRes = R.string.verdict_unverified,
        descriptionRes = R.string.verdict_desc_unverified,
        badgeTextColor = R.color.verdict_unverified_text,
        badgeBackground = R.drawable.bg_badge_verdict_unverified,
        cardBackground = R.drawable.bg_card_verdict_unverified,
        iconRes = R.drawable.ic_verdict_unverified
    ),
    CIVIC_INFO(
        titleRes = R.string.verdict_civic_info,
        descriptionRes = R.string.verdict_desc_civic_info,
        badgeTextColor = R.color.verdict_true_text,
        badgeBackground = R.drawable.bg_badge_verdict_true,
        cardBackground = R.drawable.bg_card_verdict_true,
        iconRes = R.drawable.ic_verdict_true
    );

    companion object {
        fun fromString(value: String?): VerdictType {
            return when (value?.uppercase()) {
                "FALSE", "VERIFIED_FALSE" -> VERIFIED_FALSE
                "TRUE", "VERIFIED_TRUE" -> VERIFIED_TRUE
                "MOSTLY_TRUE" -> MOSTLY_TRUE
                "MISLEADING", "BARELY_TRUE", "HALF_TRUE" -> MISLEADING
                "CIVIC_INFO", "CIVIC_GUIDE", "INFORMATION", "ANSWER", "FACT" -> CIVIC_INFO
                else -> UNVERIFIED
            }
        }
    }
}
