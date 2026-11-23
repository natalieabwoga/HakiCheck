package ke.co.hakicheck.data.repository

import ke.co.hakicheck.domain.model.CivicGuide
import ke.co.hakicheck.domain.model.CivicTopic

class CivicRepository {

    fun getCivicTopics(): List<CivicTopic> = OfflineSeedData.civicTopics

    fun getGuidesForTopic(topicId: String): List<CivicGuide> =
        OfflineSeedData.civicGuides.filter { it.topicId == topicId }

    fun getGuideById(guideId: String): CivicGuide? =
        OfflineSeedData.civicGuides.find { it.id == guideId } ?: OfflineSeedData.civicGuides.firstOrNull()

    fun getFeaturedGuide(): CivicGuide =
        OfflineSeedData.civicGuides.first()
}
