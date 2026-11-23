package ke.co.hakicheck.presentation.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ke.co.hakicheck.data.repository.SettingsRepository
import ke.co.hakicheck.domain.model.UserProfile

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    val userProfile: LiveData<UserProfile> = repository.userProfileLiveData

    fun saveProfile(name: String, county: String, serverUrl: String) {
        val current = repository.loadProfile()
        repository.saveProfile(
            current.copy(
                name = name,
                county = county,
                serverUrl = serverUrl
            )
        )
    }

    fun saveProfile(name: String, county: String, language: String, serverUrl: String) {
        val current = repository.loadProfile()
        repository.saveProfile(
            current.copy(
                name = name,
                county = county,
                language = language,
                serverUrl = serverUrl
            )
        )
    }

    fun setDarkMode(enabled: Boolean) {
        repository.setDarkMode(enabled)
    }

    fun setOfflineMode(enabled: Boolean) {
        repository.setOfflineMode(enabled)
    }

    fun setResearchConsent(enabled: Boolean) {
        repository.setResearchConsent(enabled)
    }

    fun setServerUrl(url: String) {
        repository.setServerUrl(url)
    }

    fun logout() {
        repository.logout()
    }

    class Factory(private val repository: SettingsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(repository) as T
        }
    }
}
