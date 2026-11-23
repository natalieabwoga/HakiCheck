package ke.co.hakicheck

import ke.co.hakicheck.domain.model.VerdictType
import org.junit.Assert.assertEquals
import org.junit.Test

class VerdictTypeTest {

    @Test
    fun testVerdictParsing() {
        assertEquals(VerdictType.VERIFIED_FALSE, VerdictType.fromString("FALSE"))
        assertEquals(VerdictType.VERIFIED_FALSE, VerdictType.fromString("VERIFIED_FALSE"))
        assertEquals(VerdictType.VERIFIED_TRUE, VerdictType.fromString("TRUE"))
        assertEquals(VerdictType.VERIFIED_TRUE, VerdictType.fromString("VERIFIED_TRUE"))
        assertEquals(VerdictType.MOSTLY_TRUE, VerdictType.fromString("MOSTLY_TRUE"))
        assertEquals(VerdictType.MISLEADING, VerdictType.fromString("MISLEADING"))
        assertEquals(VerdictType.MISLEADING, VerdictType.fromString("HALF_TRUE"))
        assertEquals(VerdictType.CIVIC_INFO, VerdictType.fromString("CIVIC_INFO"))
        assertEquals(VerdictType.CIVIC_INFO, VerdictType.fromString("ANSWER"))
        assertEquals(VerdictType.UNVERIFIED, VerdictType.fromString("UNKNOWN"))
        assertEquals(VerdictType.UNVERIFIED, VerdictType.fromString(null))
    }

    @Test
    fun testAllVerdictsHaveValidResources() {
        for (verdict in VerdictType.values()) {
            assert(verdict.titleRes != 0)
            assert(verdict.descriptionRes != 0)
            assert(verdict.badgeBackground != 0)
            assert(verdict.cardBackground != 0)
            assert(verdict.iconRes != 0)
        }
    }
}
