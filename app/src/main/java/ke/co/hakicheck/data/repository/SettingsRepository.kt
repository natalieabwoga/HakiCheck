package ke.co.hakicheck.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ke.co.hakicheck.domain.model.UserProfile

class SettingsRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("hakicheck_prefs", Context.MODE_PRIVATE)

    private val _userProfileLiveData = MutableLiveData<UserProfile>()
    val userProfileLiveData: LiveData<UserProfile> = _userProfileLiveData

    init {
        loadProfile()
    }

    fun loadProfile(): UserProfile {
        val profile = UserProfile(
            name = prefs.getString(KEY_NAME, "Citizen") ?: "Citizen",
            email = prefs.getString(KEY_EMAIL, "") ?: "",
            county = prefs.getString(KEY_COUNTY, "47 - Nairobi City") ?: "47 - Nairobi City",
            language = prefs.getString(KEY_LANGUAGE, "English (Official)") ?: "English (Official)",
            isDarkMode = prefs.getBoolean(KEY_DARK_MODE, false),
            isOfflineMode = prefs.getBoolean(KEY_OFFLINE_MODE, false),
            hasResearchConsent = prefs.getBoolean(KEY_CONSENT, true),
            serverUrl = prefs.getString(KEY_SERVER_URL, "http://10.0.2.2:8000/") ?: "http://10.0.2.2:8000/",
            isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        )
        _userProfileLiveData.postValue(profile)
        return profile
    }

    fun saveProfile(profile: UserProfile) {
        prefs.edit()
            .putString(KEY_NAME, profile.name)
            .putString(KEY_EMAIL, profile.email)
            .putString(KEY_COUNTY, profile.county)
            .putString(KEY_LANGUAGE, profile.language)
            .putBoolean(KEY_DARK_MODE, profile.isDarkMode)
            .putBoolean(KEY_OFFLINE_MODE, profile.isOfflineMode)
            .putBoolean(KEY_CONSENT, profile.hasResearchConsent)
            .putString(KEY_SERVER_URL, profile.serverUrl)
            .putBoolean(KEY_IS_LOGGED_IN, profile.isLoggedIn)
            .apply()
        _userProfileLiveData.postValue(profile)
    }

    fun login(name: String, email: String, county: String) {
        prefs.edit()
            .putString(KEY_NAME, name.ifEmpty { "Citizen" })
            .putString(KEY_EMAIL, email)
            .putString(KEY_COUNTY, county.ifEmpty { "47 - Nairobi City" })
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
        loadProfile()
    }

    fun logout() {
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .apply()
        loadProfile()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
        loadProfile()
    }

    fun setOfflineMode(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_OFFLINE_MODE, enabled).apply()
        loadProfile()
    }

    fun setResearchConsent(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_CONSENT, enabled).apply()
        loadProfile()
    }

    fun setServerUrl(url: String) {
        prefs.edit().putString(KEY_SERVER_URL, url).apply()
        loadProfile()
    }

    fun getServerUrl(): String {
        return prefs.getString(KEY_SERVER_URL, "http://10.0.2.2:8000/") ?: "http://10.0.2.2:8000/"
    }

    companion object {
        private const val KEY_NAME = "key_name"
        private const val KEY_EMAIL = "key_email"
        private const val KEY_COUNTY = "key_county"
        private const val KEY_LANGUAGE = "key_language"
        private const val KEY_DARK_MODE = "key_dark_mode"
        private const val KEY_OFFLINE_MODE = "key_offline_mode"
        private const val KEY_CONSENT = "key_consent"
        private const val KEY_SERVER_URL = "key_server_url"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }
}
