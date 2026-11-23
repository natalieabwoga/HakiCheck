package ke.co.hakicheck.presentation.ui.verify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ke.co.hakicheck.data.repository.FactCheckRepository
import ke.co.hakicheck.domain.model.ClaimVerification
import kotlinx.coroutines.launch

class VerificationResultViewModel(
    private val repository: FactCheckRepository,
    private val verification: ClaimVerification
) : ViewModel() {

    private val _isSaved = MutableLiveData(verification.isSaved)
    val isSaved: LiveData<Boolean> = _isSaved

    init {
        viewModelScope.launch {
            _isSaved.value = repository.isClaimSaved(verification.id)
        }
    }

    fun toggleSave() {
        viewModelScope.launch {
            val newState = repository.toggleSaveClaim(verification)
            _isSaved.value = newState
        }
    }

    class Factory(
        private val repository: FactCheckRepository,
        private val verification: ClaimVerification
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VerificationResultViewModel(repository, verification) as T
        }
    }
}
