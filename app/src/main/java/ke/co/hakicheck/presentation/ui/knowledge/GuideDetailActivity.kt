package ke.co.hakicheck.presentation.ui.knowledge

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import ke.co.hakicheck.databinding.ActivityGuideDetailBinding
import ke.co.hakicheck.domain.model.CivicGuide

class GuideDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuideDetailBinding

    private val guide: CivicGuide by lazy {
        @Suppress("DEPRECATION")
        intent.getSerializableExtra(EXTRA_GUIDE) as CivicGuide
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        populateGuide()
    }

    private fun setupToolbar() {
        binding.guideToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun populateGuide() {
        binding.tvGuideTitle.text = guide.title
        binding.tvGuideSubtitle.text = guide.subtitle
        binding.tvGuideCitation.text = guide.primaryCitation
        binding.tvGuideReadTime.text = "• ${guide.readTimeMinutes} min read"

        // Format key takeaways
        val takeawaysText = guide.keyTakeaways.joinToString("\n\n") { "• $it" }
        binding.tvKeyTakeaways.text = takeawaysText

        // Render HTML content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvGuideContent.text = Html.fromHtml(guide.fullContentHtml, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            binding.tvGuideContent.text = Html.fromHtml(guide.fullContentHtml)
        }
    }

    companion object {
        const val EXTRA_GUIDE = "extra_guide"
    }
}
