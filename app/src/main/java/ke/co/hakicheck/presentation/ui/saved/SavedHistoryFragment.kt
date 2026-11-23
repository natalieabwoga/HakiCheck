package ke.co.hakicheck.presentation.ui.saved

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.hakicheck.HakiCheckApplication
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.FragmentSavedHistoryBinding
import ke.co.hakicheck.domain.model.ClaimVerification
import ke.co.hakicheck.domain.model.UserProfile
import ke.co.hakicheck.presentation.ui.auth.AuthActivity
import ke.co.hakicheck.presentation.ui.verify.VerificationResultActivity
import kotlinx.coroutines.launch

class SavedHistoryFragment : Fragment() {

    private var _binding: FragmentSavedHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SavedClaimsAdapter
    private var allSaved: List<ClaimVerification> = emptyList()
    private var currentUserProfile: UserProfile? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as HakiCheckApplication

        setupRecyclerView(app)
        setupSearch()
        setupClearAll(app)
        setupAuthListeners(app)
        observeAuthState(app)
        observeSavedClaims(app)
    }

    private fun setupAuthListeners(app: HakiCheckApplication) {
        binding.btnAuthRequiredLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }

        binding.btnSavedLogout.setOnClickListener {
            app.settingsRepository.logout()
        }
    }

    private fun observeAuthState(app: HakiCheckApplication) {
        app.settingsRepository.userProfileLiveData.observe(viewLifecycleOwner) { profile ->
            currentUserProfile = profile
            updateAuthUi(profile)
        }
    }

    private fun updateAuthUi(profile: UserProfile?) {
        val isLoggedIn = profile?.isLoggedIn == true
        if (isLoggedIn) {
            binding.layoutAuthRequired.visibility = View.GONE
            binding.layoutSearchSaved.visibility = View.VISIBLE
            binding.layoutUserLoggedInBanner.visibility = View.VISIBLE
            binding.tvSavedUserName.text = profile?.name ?: "Verified Citizen"
            binding.tvSavedUserCounty.text = profile?.county ?: "47 - Nairobi City"
            val query = binding.etSearchSaved.text?.toString()?.trim() ?: ""
            filterList(query)
        } else {
            binding.layoutAuthRequired.visibility = View.VISIBLE
            binding.layoutSearchSaved.visibility = View.GONE
            binding.layoutUserLoggedInBanner.visibility = View.GONE
            binding.rvSavedClaims.visibility = View.GONE
            binding.layoutEmptyState.visibility = View.GONE
            binding.btnClearAllSaved.visibility = View.GONE
        }
    }

    private fun setupRecyclerView(app: HakiCheckApplication) {
        adapter = SavedClaimsAdapter(
            onItemClick = { item ->
                val intent = Intent(requireContext(), VerificationResultActivity::class.java).apply {
                    putExtra(VerificationResultActivity.EXTRA_VERIFICATION, item)
                }
                startActivity(intent)
            },
            onDeleteClick = { item ->
                viewLifecycleOwner.lifecycleScope.launch {
                    app.factCheckRepository.toggleSaveClaim(item)
                }
            }
        )

        binding.rvSavedClaims.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@SavedHistoryFragment.adapter
        }
    }

    private fun setupSearch() {
        binding.etSearchSaved.doAfterTextChanged { text ->
            val query = text?.toString()?.trim() ?: ""
            filterList(query)
        }
    }

    private fun filterList(query: String) {
        if (currentUserProfile?.isLoggedIn != true) return

        if (query.isEmpty()) {
            adapter.updateList(allSaved)
            updateEmptyState(allSaved.isEmpty(), false)
        } else {
            val filtered = allSaved.filter {
                it.claimText.contains(query, ignoreCase = true) ||
                it.summary.contains(query, ignoreCase = true) ||
                it.evidenceSources.any { src -> src.title.contains(query, ignoreCase = true) }
            }
            adapter.updateList(filtered)
            updateEmptyState(filtered.isEmpty(), true)
        }
    }

    private fun updateEmptyState(isEmpty: Boolean, isSearching: Boolean) {
        if (currentUserProfile?.isLoggedIn != true) return

        if (isEmpty) {
            binding.layoutEmptyState.visibility = View.VISIBLE
            binding.rvSavedClaims.visibility = View.GONE
            binding.btnClearAllSaved.visibility = View.GONE
        } else {
            binding.layoutEmptyState.visibility = View.GONE
            binding.rvSavedClaims.visibility = View.VISIBLE
            binding.btnClearAllSaved.visibility = if (isSearching) View.GONE else View.VISIBLE
        }
    }

    private fun setupClearAll(app: HakiCheckApplication) {
        binding.btnClearAllSaved.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.saved_clear_all)
                .setMessage(R.string.saved_clear_confirm)
                .setPositiveButton(R.string.saved_clear_yes) { _, _ ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        app.factCheckRepository.clearAllSaved()
                    }
                }
                .setNegativeButton(R.string.saved_clear_cancel, null)
                .show()
        }
    }

    private fun observeSavedClaims(app: HakiCheckApplication) {
        app.factCheckRepository.savedClaims.observe(viewLifecycleOwner) { list ->
            allSaved = list ?: emptyList()
            if (currentUserProfile?.isLoggedIn == true) {
                val currentQuery = binding.etSearchSaved.text?.toString()?.trim() ?: ""
                filterList(currentQuery)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val app = requireActivity().application as HakiCheckApplication
        updateAuthUi(app.settingsRepository.loadProfile())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
