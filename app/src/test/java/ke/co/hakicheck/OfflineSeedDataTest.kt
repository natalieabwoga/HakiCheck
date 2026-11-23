package ke.co.hakicheck

import ke.co.hakicheck.data.repository.OfflineSeedData
import ke.co.hakicheck.domain.model.VerdictType
import org.junit.Assert.*
import org.junit.Test

class OfflineSeedDataTest {

    @Test
    fun testUnderageVotingClaimIsFalse() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "I can vote under the age of 18"
        )
        assertEquals(VerdictType.VERIFIED_FALSE, result.verdict)
        assertTrue(result.evidenceSources.isNotEmpty())
        assertTrue(result.evidenceSources.any { it.legalCitation.contains("Art. 83") || it.legalCitation.contains("Art. 260") })
    }

    @Test
    fun testVoterRegistrationFeeClaimIsFalse() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Kenyan citizens must pay Ksh 500 fee to register as a voter in 2027."
        )
        assertEquals(VerdictType.VERIFIED_FALSE, result.verdict)
        assertTrue(result.evidenceSources.isNotEmpty())
        assertTrue(result.evidenceSources.any { it.legalCitation.contains("Art. 38") })
    }

    @Test
    fun testElectronicVotingClaimIsMisleading() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "IEBC announced electronic voting replaces ballot papers completely."
        )
        assertEquals(VerdictType.MISLEADING, result.verdict)
        assertTrue(result.evidenceSources.any { it.title.contains("Elections Act") })
    }

    @Test
    fun testMcaDegreeRequirementClaimIsTrue() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Any registered Kenyan citizen aged 18+ can run for County Assembly without a degree."
        )
        assertEquals(VerdictType.VERIFIED_TRUE, result.verdict)
        assertTrue(result.evidenceSources.any { it.title.contains("High Court") })
    }

    @Test
    fun testMainaKiaiPollingStationResultsClaimIsTrue() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Presidential election results announced at the polling station are final."
        )
        assertEquals(VerdictType.VERIFIED_TRUE, result.verdict)
        assertTrue(result.evidenceSources.any { it.title.contains("Maina Kiai") })
    }

    @Test
    fun testAmbiguousClaimFallsBackToUnverified() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "The moon landing was broadcast on Kenyan radio in 1969."
        )
        assertEquals(VerdictType.UNVERIFIED, result.verdict)
        assertTrue(result.confidenceScore < 60)
    }

    @Test
    fun testCivicTopicsArePopulated() {
        val topics = OfflineSeedData.civicTopics
        assertTrue("Should have at least 4 civic topics", topics.size >= 4)
        for (topic in topics) {
            val matchingGuides = OfflineSeedData.civicGuides.filter { it.topicId == topic.id }
            assertTrue("Topic ${topic.id} must have at least one guide", matchingGuides.isNotEmpty())
        }
    }

    @Test
    fun testTrendingClaimsHaveValidVerifications() {
        val trending = OfflineSeedData.trendingClaims
        assertTrue(trending.size >= 8)
        for (item in trending) {
            assertNotNull(item.claimTitle)
            assertNotNull(item.verdict)
            assertTrue(item.viralCount > 0)
            assertEquals("Verification claim must match trending claim", item.verdict, item.verification.verdict)
            assertTrue(item.verification.evidenceSources.isNotEmpty())
        }
    }

    @Test
    fun testLindaMamaTrendingClaim() {
        val item = OfflineSeedData.trendingClaims.find { it.id == "trend_linda_mama" }
        assertNotNull(item)
        assertEquals(VerdictType.MISLEADING, item!!.verdict)
        assertTrue(item.verification.summary.contains("Linda Mama"))
    }

    @Test
    fun testSifunaTrendingClaim() {
        val item = OfflineSeedData.trendingClaims.find { it.id == "trend_sifuna_protest" }
        assertNotNull(item)
        assertEquals(VerdictType.VERIFIED_FALSE, item!!.verdict)
        assertTrue(item.verification.summary.contains("Sifuna"))
    }

    @Test
    fun testKiswahiliElectionDateQuestion() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Tarehe ya uchaguzi mkuu ni lini?"
        )
        assertEquals(VerdictType.CIVIC_INFO, result.verdict)
        assertTrue(result.summary.contains("Constitution of Kenya 2010") || result.summary.contains("August"))
    }

    @Test
    fun testShengRailaPresidentClaimIsFalse() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Raila ndiye prezo wa sasa"
        )
        assertEquals(VerdictType.VERIFIED_FALSE, result.verdict)
        assertTrue(result.summary.contains("William Samoei Ruto") || result.summary.contains("Article 141"))
    }

    @Test
    fun testShengVoterRegistrationIsFree() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Kadi ya kura inatoka na chapaa ngapi?"
        )
        assertEquals(VerdictType.CIVIC_INFO, result.verdict)
        assertTrue(result.summary.contains("free") || result.summary.contains("Article 38"))
    }

    @Test
    fun testKiswahiliUnderageVotingIsFalse() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Naeza piga kura nikiwa na miaka 17?"
        )
        assertEquals(VerdictType.VERIFIED_FALSE, result.verdict)
        assertTrue(result.summary.contains("18"))
    }

    @Test
    fun testDiasporaCannotVoteIsFalse() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Mtu wa diaspora hawezi piga kura"
        )
        assertEquals(VerdictType.VERIFIED_FALSE, result.verdict)
        assertTrue(result.summary.contains("Article 38"))
    }

    @Test
    fun testDiasporaCanVoteIsTrue() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Mtu wa diaspora anaweza piga kura akiwa majuu"
        )
        assertEquals(VerdictType.VERIFIED_TRUE, result.verdict)
        assertTrue(result.summary.contains("Article 38"))
    }

    @Test
    fun testDiasporaQuestionIsCivicInfo() {
        val result = OfflineSeedData.findOrGenerateVerification(
            "Je, mtu wa diaspora anaweza piga kura?"
        )
        assertEquals(VerdictType.CIVIC_INFO, result.verdict)
        assertTrue(result.summary.contains("Article 38") || result.summary.contains("diaspora"))
    }
}
