package ke.co.hakicheck.domain.model

import java.io.Serializable

data class CivicTopic(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val guideCount: Int,
    val legalBasis: String
) : Serializable

data class CivicGuide(
    val id: String,
    val topicId: String,
    val title: String,
    val subtitle: String,
    val readTimeMinutes: Int,
    val primaryCitation: String,
    val fullContentHtml: String,
    val keyTakeaways: List<String>
) : Serializable
