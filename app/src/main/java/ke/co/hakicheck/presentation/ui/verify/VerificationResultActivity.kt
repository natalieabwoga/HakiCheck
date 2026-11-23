package ke.co.hakicheck.presentation.ui.verify

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.hakicheck.HakiCheckApplication
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.ActivityVerificationResultBinding
import ke.co.hakicheck.domain.model.ClaimVerification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VerificationResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationResultBinding
    private lateinit var evidenceAdapter: EvidenceSourceAdapter

    private val verification: ClaimVerification by lazy {
        @Suppress("DEPRECATION")
        intent.getSerializableExtra(EXTRA_VERIFICATION) as ClaimVerification
    }

    private val viewModel: VerificationResultViewModel by viewModels {
        val app = application as HakiCheckApplication
        VerificationResultViewModel.Factory(app.factCheckRepository, verification)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        populateData()
        setupEvidenceRecyclerView()
        setupActions()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.resultToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun populateData() {
        val query = verification.claimText.trim()
        val isQuestion = query.endsWith("?") || listOf("who", "what", "when", "where", "why", "how", "is it", "can", "does", "which").any {
            query.lowercase().startsWith(it)
        }
        binding.tvClaimLabel.text = if (isQuestion) "SUBMITTED INQUIRY" else "ANALYZED CLAIM"
        binding.tvClaimText.text = "“$query”"
        binding.tvSummaryBody.text = verification.summary

        val verdict = verification.verdict
        binding.tvVerdictBadge.setText(verdict.titleRes)
        binding.tvVerdictBadge.setBackgroundResource(verdict.badgeBackground)
        binding.tvVerdictBadge.setTextColor(
            ContextCompat.getColor(this, verdict.badgeTextColor)
        )
        binding.cardVerdict.strokeColor =
            ContextCompat.getColor(this, verdict.badgeTextColor)

        val dateFormat = SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault())
        binding.tvCheckedTimestamp.text = dateFormat.format(Date(verification.checkedAtMillis))

        if (verification.evidenceSources.isEmpty()) {
            binding.layoutEvidenceSection.visibility = View.GONE
            binding.tvConfidenceBadge.text = "Civic Scope Notice • No Legal Citations Applicable"
        } else {
            binding.layoutEvidenceSection.visibility = View.VISIBLE
            binding.tvEvidenceCount.text = getString(
                R.string.result_evidence_count,
                verification.evidenceSources.size
            )
            binding.tvConfidenceBadge.text = getString(
                R.string.result_confidence,
                verification.confidenceScore,
                "Kenyan Constitution & Statutory Law"
            )
        }
    }

    private fun setupEvidenceRecyclerView() {
        if (verification.evidenceSources.isNotEmpty()) {
            evidenceAdapter = EvidenceSourceAdapter(verification.evidenceSources) { source ->
                val sheet = CitationDetailBottomSheet.newInstance(source)
                sheet.show(supportFragmentManager, "CitationDetailBottomSheet")
            }
            binding.rvEvidenceSources.apply {
                layoutManager = LinearLayoutManager(this@VerificationResultActivity)
                adapter = evidenceAdapter
            }
        }
    }

    private fun setupActions() {
        binding.btnCopyResult.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val textToCopy = ke.co.hakicheck.util.ShareFormatter.formatForWhatsApp(this, verification)
            val clip = ClipData.newPlainText("HakiCheck Verification", textToCopy)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, R.string.result_copied_toast, Toast.LENGTH_SHORT).show()
        }

        binding.btnShareResult.setOnClickListener {
            val shareText = ke.co.hakicheck.util.ShareFormatter.formatForWhatsApp(this, verification)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "HakiCheck Fact-Check: ${getString(verification.verdict.titleRes)}"
                )
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.result_action_share)))
        }

        binding.btnSaveResult.setOnClickListener {
            val app = application as HakiCheckApplication
            if (!app.settingsRepository.loadProfile().isLoggedIn) {
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Account Required")
                    .setMessage("Please sign in or create an account to bookmark verified claims and access your legal evidence trails.")
                    .setPositiveButton("Sign In / Register") { _, _ ->
                        startActivity(Intent(this, ke.co.hakicheck.presentation.ui.auth.AuthActivity::class.java))
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            } else {
                viewModel.toggleSave()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isSaved.observe(this) { saved ->
            if (saved) {
                binding.btnSaveResult.setImageResource(R.drawable.ic_bookmark_filled)
                binding.btnSaveResult.setColorFilter(
                    ContextCompat.getColor(this, R.color.primary_green)
                )
            } else {
                binding.btnSaveResult.setImageResource(R.drawable.ic_bookmark)
                binding.btnSaveResult.setColorFilter(
                    ContextCompat.getColor(this, R.color.text_primary)
                )
            }
        }
    }

    companion object {
        const val EXTRA_VERIFICATION = "extra_verification"
    }
}
