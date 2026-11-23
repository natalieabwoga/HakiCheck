package ke.co.hakicheck.presentation.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ke.co.hakicheck.HakiCheckApplication
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.FragmentSettingsBinding
import ke.co.hakicheck.presentation.ui.auth.AuthActivity

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels {
        val app = requireActivity().application as HakiCheckApplication
        SettingsViewModel.Factory(app.settingsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupSwitches()
        setupSaveButton()
        setupAuthButtons()
        setupEthicsTermsLink()
        observeProfile()
    }

    private fun setupAuthButtons() {
        binding.btnSettingsLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }

        binding.btnSettingsLogout.setOnClickListener {
            viewModel.logout()
            Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupEthicsTermsLink() {
        binding.layoutEthicsTerms.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.settings_terms_title)
                .setMessage(
                    """
                    1. Institutional Research & Public Good:
                    HakiCheck is developed as an academic and civic innovation project at Strathmore University (School of Computing and Engineering Sciences - SCES).

                    2. Informed Consent & Data Minimization:
                    Query telemetry is strictly voluntary and anonymous. When enabled, only non-identifiable claim keywords are analyzed to improve verification accuracy. You can disable tracking at any time using the toggle above.

                    3. Non-Partisan Impartiality:
                    All verdicts and citations are deterministically grounded on the Constitution of Kenya 2010, Acts of Parliament, and verified judgments of Kenyan superior courts. HakiCheck does not generate political opinion or endorse any political party.

                    4. Privacy Protection:
                    No personal identifiers (PII), phone numbers, or passwords are ever sold or shared with third parties.
                    """.trimIndent()
                )
                .setPositiveButton("I Understand", null)
                .show()
        }
    }

    private fun setupSpinners() {
        val countyAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.kenya_counties,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerCounty.adapter = countyAdapter
    }

    private fun setupSwitches() {
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkMode(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.switchOfflineMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setOfflineMode(isChecked)
            binding.layoutServerConfig.visibility = if (isChecked) View.GONE else View.VISIBLE
            val modeText = if (isChecked) "Offline Demonstration Mode Active" else "Live RAG Server Mode Active"
            Toast.makeText(requireContext(), modeText, Toast.LENGTH_SHORT).show()
        }

        binding.switchResearchConsent.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setResearchConsent(isChecked)
        }
    }

    private fun setupSaveButton() {
        binding.btnSaveProfile.setOnClickListener {
            val name = binding.etCitizenName.text.toString().trim()
            val county = binding.spinnerCounty.selectedItem?.toString() ?: "47 - Nairobi City"
            val serverUrl = binding.etServerUrl.text.toString().trim().ifEmpty { "http://10.0.2.2:8000/" }

            viewModel.saveProfile(name, county, serverUrl)
            Toast.makeText(requireContext(), R.string.settings_profile_saved, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeProfile() {
        viewModel.userProfile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                val isLoggedIn = it.isLoggedIn
                if (isLoggedIn) {
                    binding.cardGuestAccountPrompt.visibility = View.GONE
                    binding.layoutCitizenProfileSection.visibility = View.VISIBLE
                    binding.etCitizenName.setText(it.name)
                    binding.tvCitizenEmail.text = it.email.ifEmpty { "Logged in citizen" }

                    val counties = resources.getStringArray(R.array.kenya_counties)
                    val countyIndex = counties.indexOf(it.county)
                    if (countyIndex >= 0) {
                        binding.spinnerCounty.setSelection(countyIndex)
                    }
                } else {
                    binding.cardGuestAccountPrompt.visibility = View.VISIBLE
                    binding.layoutCitizenProfileSection.visibility = View.GONE
                }

                binding.etServerUrl.setText(it.serverUrl)
                binding.switchDarkMode.isChecked = it.isDarkMode
                binding.switchOfflineMode.isChecked = it.isOfflineMode
                binding.layoutServerConfig.visibility = if (it.isOfflineMode) View.GONE else View.VISIBLE
                binding.switchResearchConsent.isChecked = it.hasResearchConsent
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
