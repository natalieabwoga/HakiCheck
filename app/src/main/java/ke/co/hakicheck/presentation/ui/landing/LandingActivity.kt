package ke.co.hakicheck.presentation.ui.landing

import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.ActivityLandingBinding
import ke.co.hakicheck.presentation.ui.main.MainActivity

/**
 * Landing Screen for HakiCheck.
 * Features solely the animated HakiCheck GIF branding and the "Get Started" action.
 */
class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding
    private var hasNavigated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize and play the animated GIF
        playLandingAnimation()

        // "Get Started" button action
        binding.btnGetStarted.setOnClickListener {
            navigateToMain()
        }

        // Tapping anywhere on the screen also proceeds directly
        binding.landingRoot.setOnClickListener {
            navigateToMain()
        }
    }

    private fun playLandingAnimation() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(resources, R.raw.hakicheck_landing)
                val drawable = ImageDecoder.decodeDrawable(source)
                binding.ivLandingGif.setImageDrawable(drawable)
                if (drawable is AnimatedImageDrawable) {
                    drawable.repeatCount = AnimatedImageDrawable.REPEAT_INFINITE
                    drawable.start()
                }
            } else {
                binding.ivLandingGif.setImageResource(R.drawable.hakicheck_logo)
            }
        } catch (e: Throwable) {
            Log.w("LandingActivity", "Falling back to static logo", e)
            binding.ivLandingGif.setImageResource(R.drawable.hakicheck_logo)
        }
    }

    private fun navigateToMain() {
        if (hasNavigated) return
        hasNavigated = true
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        @Suppress("DEPRECATION")
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
