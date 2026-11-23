package ke.co.hakicheck.presentation.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ke.co.hakicheck.HakiCheckApplication
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.ActivityAuthBinding

/**
 * Authentication Activity providing Sign In and Sign Up options
 * matching the official HakiCheck Kenya civic design palette.
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private var isSignUpMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupCountySpinner()
        setupTabSwitching()
        setupActions()

        // Check if pre-configured for sign up
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode == MODE_SIGN_UP) {
            setMode(signUp = true)
        } else {
            setMode(signUp = false)
        }
    }

    private fun setupToolbar() {
        binding.authToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupCountySpinner() {
        val countyAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.kenya_counties,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerAuthCounty.adapter = countyAdapter
    }

    private fun setupTabSwitching() {
        binding.tabSignIn.setOnClickListener {
            setMode(signUp = false)
        }

        binding.tabSignUp.setOnClickListener {
            setMode(signUp = true)
        }

        binding.tvToggleMode.setOnClickListener {
            setMode(signUp = !isSignUpMode)
        }

        binding.btnContinueGuest.setOnClickListener {
            finish()
        }

        binding.tvAuthTermsLink.setOnClickListener {
            showEthicsDialog()
        }
    }

    private fun setMode(signUp: Boolean) {
        isSignUpMode = signUp
        if (signUp) {
            binding.tabSignUp.setBackgroundResource(R.drawable.bg_chip_selected)
            binding.tabSignUp.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tabSignIn.setBackgroundResource(android.R.color.transparent)
            binding.tabSignIn.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))

            binding.layoutNameInput.visibility = View.VISIBLE
            binding.layoutCountyInput.visibility = View.VISIBLE
            binding.layoutAuthEthics.visibility = View.VISIBLE
            binding.btnSubmitAuth.text = "Create Account"
            binding.tvToggleMode.text = "Already have an account? Sign In"
            binding.tvAuthHeaderSubtitle.text = "Create your citizen account to save and track claims"
        } else {
            binding.tabSignIn.setBackgroundResource(R.drawable.bg_chip_selected)
            binding.tabSignIn.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.tabSignUp.setBackgroundResource(android.R.color.transparent)
            binding.tabSignUp.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))

            binding.layoutNameInput.visibility = View.GONE
            binding.layoutCountyInput.visibility = View.GONE
            binding.layoutAuthEthics.visibility = View.GONE
            binding.btnSubmitAuth.text = "Sign In"
            binding.tvToggleMode.text = "Don't have an account? Sign Up"
            binding.tvAuthHeaderSubtitle.text = "Sign in to access your saved claims and legal citations"
        }
    }

    private fun setupActions() {
        binding.btnSubmitAuth.setOnClickListener {
            val email = binding.etAuthEmail.text.toString().trim()
            val password = binding.etAuthPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etAuthEmail.error = "Please enter your email or phone number"
                return@setOnClickListener
            }

            if (password.length < 4) {
                binding.etAuthPassword.error = "Password must be at least 4 characters"
                return@setOnClickListener
            }

            val app = application as HakiCheckApplication
            val name = if (isSignUpMode) {
                val inputName = binding.etAuthName.text.toString().trim()
                if (inputName.isNotEmpty()) inputName else "John Doe"
            } else {
                val existing = app.settingsRepository.loadProfile().name
                if (existing.isNotEmpty() && existing != "Citizen") existing else email.substringBefore("@").replace(".", " ").capitalizeWords()
            }

            val county = if (isSignUpMode) {
                binding.spinnerAuthCounty.selectedItem?.toString() ?: "47 - Nairobi City"
            } else {
                app.settingsRepository.loadProfile().county
            }

            if (isSignUpMode) {
                val consent = binding.cbAuthResearchConsent.isChecked
                app.settingsRepository.setResearchConsent(consent)
            }

            app.settingsRepository.login(name = name, email = email, county = county)

            val welcomeMsg = if (isSignUpMode) "Welcome to HakiCheck, $name!" else "Signed in as $name"
            Toast.makeText(this, welcomeMsg, Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun showEthicsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Civic Research Ethics & Terms of Use")
            .setMessage(
                """
                1. Institutional Research & Public Good:
                HakiCheck is developed as an academic and civic innovation project at Strathmore University (School of Computing and Engineering Sciences - SCES).

                2. Informed Consent & Data Minimization:
                Query telemetry is strictly voluntary and anonymous. When enabled, only non-identifiable claim keywords are analyzed to improve verification accuracy. You can disable tracking at any time in Settings.

                3. Non-Partisan Impartiality:
                All verdicts and citations are deterministically grounded on the Constitution of Kenya 2010, Acts of Parliament, and verified judgments of Kenyan superior courts. HakiCheck does not generate political opinion or endorse any political party.

                4. Privacy Protection:
                No personal identifiers (PII), phone numbers, or passwords are ever sold or shared with third parties.
                """.trimIndent()
            )
            .setPositiveButton("I Understand", null)
            .show()
    }

    private fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }

    companion object {
        const val EXTRA_MODE = "extra_mode"
        const val MODE_SIGN_IN = "mode_sign_in"
        const val MODE_SIGN_UP = "mode_sign_up"
    }
}
