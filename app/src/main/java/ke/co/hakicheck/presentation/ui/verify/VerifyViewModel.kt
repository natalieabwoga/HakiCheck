package ke.co.hakicheck.presentation.ui.verify

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ke.co.hakicheck.data.repository.FactCheckRepository
import ke.co.hakicheck.data.repository.SettingsRepository
import ke.co.hakicheck.domain.model.ClaimVerification
import ke.co.hakicheck.util.NetworkUtils
import kotlinx.coroutines.launch

class VerifyViewModel(
    private val repository: FactCheckRepository,
    private val settingsRepository: SettingsRepository,
    private val context: Context
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _verificationResult = MutableLiveData<ClaimVerification?>()
    val verificationResult: LiveData<ClaimVerification?> = _verificationResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun verifyClaim(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            _errorMessage.value = "Please enter or paste a claim first."
            return
        }
        if (trimmed.length < 10) {
            _errorMessage.value = "Please enter at least 10 characters to verify."
            return
        }

        val profile = settingsRepository.loadProfile()
        if (!profile.isOfflineMode && !NetworkUtils.isNetworkAvailable(context)) {
            _errorMessage.value = "No active internet connection. Switched to offline legal corpus for this check."
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = repository.verifyClaim(trimmed)
                _verificationResult.value = result
            } catch (e: Exception) {
                _errorMessage.value = "Verification error: ${e.localizedMessage ?: "Unknown error"}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    class Factory(
        private val repository: FactCheckRepository,
        private val settingsRepository: SettingsRepository,
        private val context: Context
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VerifyViewModel(repository, settingsRepository, context) as T
        }
    }
}
