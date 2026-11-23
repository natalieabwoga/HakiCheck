package ke.co.hakicheck.domain.model

data class UserProfile(
    val name: String = "Citizen",
    val email: String = "",
    val county: String = "47 - Nairobi City",
    val language: String = "English (Official)",
    val isDarkMode: Boolean = false,
    val isOfflineMode: Boolean = true, // Default to true so student presentations work 100% offline
    val hasResearchConsent: Boolean = true,
    val serverUrl: String = "http://10.0.2.2:8000/",
    val isLoggedIn: Boolean = false
)
