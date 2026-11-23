package ke.co.hakicheck.presentation.ui.verify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ke.co.hakicheck.HakiCheckApplication
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.FragmentVerifyBinding
import ke.co.hakicheck.domain.model.ClaimVerification

class VerifyFragment : Fragment() {

    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VerifyViewModel by viewModels {
        val app = requireActivity().application as HakiCheckApplication
        VerifyViewModel.Factory(app.factCheckRepository, app.settingsRepository, app.applicationContext)
    }

    private var latestVerification: ClaimVerification? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInput()
        setupSampleChips()
        setupVerifyButton()
        observeViewModel()
    }

    private fun setupInput() {
        binding.etClaimInput.doAfterTextChanged { text ->
            val count = text?.length ?: 0
            binding.tvCharCounter.text = "$count/600"
            binding.btnClearInput.visibility = if (count > 0) View.VISIBLE else View.GONE
        }

        binding.btnClearInput.setOnClickListener {
            binding.etClaimInput.text.clear()
        }
    }

    private fun setupSampleChips() {
        binding.chipSample1.setOnClickListener {
            binding.etClaimInput.setText(binding.chipSample1.text.toString().trim('“', '”'))
            binding.etClaimInput.setSelection(binding.etClaimInput.text.length)
        }

        binding.chipSample2.setOnClickListener {
            binding.etClaimInput.setText(binding.chipSample2.text.toString().trim('“', '”'))
            binding.etClaimInput.setSelection(binding.etClaimInput.text.length)
        }

        binding.chipSample3.setOnClickListener {
            binding.etClaimInput.setText(binding.chipSample3.text.toString().trim('“', '”'))
            binding.etClaimInput.setSelection(binding.etClaimInput.text.length)
        }
    }

    private fun setupVerifyButton() {
        binding.btnVerify.setOnClickListener {
            val claim = binding.etClaimInput.text.toString()
            viewModel.verifyClaim(claim)
        }

        binding.cardRecentPreview.setOnClickListener {
            latestVerification?.let { openResult(it) }
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.btnVerify.isEnabled = !loading
            binding.btnVerify.text = if (loading) "" else getString(R.string.verify_btn_action)
            binding.progressBarVerify.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.verificationResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                latestVerification = it
                updateRecentPreview(it)
                openResult(it)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateRecentPreview(verification: ClaimVerification) {
        binding.layoutRecentPreview.visibility = View.VISIBLE
        binding.tvPreviewClaim.text = "“${verification.claimText}”"
        binding.tvPreviewSummary.text = verification.summary

        val verdict = verification.verdict
        binding.tvPreviewBadge.setText(verdict.titleRes)
        binding.tvPreviewBadge.setBackgroundResource(verdict.badgeBackground)
        binding.tvPreviewBadge.setTextColor(
            ContextCompat.getColor(requireContext(), verdict.badgeTextColor)
        )
        binding.cardRecentPreview.strokeColor =
            ContextCompat.getColor(requireContext(), verdict.badgeTextColor)
    }

    private fun openResult(verification: ClaimVerification) {
        val intent = Intent(requireContext(), VerificationResultActivity::class.java).apply {
            putExtra(VerificationResultActivity.EXTRA_VERIFICATION, verification)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
