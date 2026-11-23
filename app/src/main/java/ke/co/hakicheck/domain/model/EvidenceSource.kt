package ke.co.hakicheck.domain.model

import java.io.Serializable

data class EvidenceSource(
    val title: String,
    val legalCitation: String,
    val extractSnippet: String,
    val documentType: String, // "CONSTITUTION", "STATUTE", "GAZETTE", "REGULATION"
    val sourceUrl: String? = null
) : Serializable
