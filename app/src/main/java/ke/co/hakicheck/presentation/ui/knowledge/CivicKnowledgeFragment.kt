package ke.co.hakicheck.presentation.ui.knowledge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.hakicheck.data.repository.CivicRepository
import ke.co.hakicheck.databinding.FragmentCivicKnowledgeBinding
import ke.co.hakicheck.domain.model.CivicGuide
import ke.co.hakicheck.domain.model.CivicTopic

class CivicKnowledgeFragment : Fragment() {

    private var _binding: FragmentCivicKnowledgeBinding? = null
    private val binding get() = _binding!!

    private val civicRepository = CivicRepository()
    private lateinit var topicAdapter: CivicTopicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCivicKnowledgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFeaturedBanner()
        setupTopicsRecyclerView()
    }

    private fun setupFeaturedBanner() {
        val featuredGuide = civicRepository.getFeaturedGuide()

        binding.cardFeaturedGuide.setOnClickListener {
            openGuideDetail(featuredGuide)
        }

        binding.btnStartLearning.setOnClickListener {
            openGuideDetail(featuredGuide)
        }
    }

    private fun setupTopicsRecyclerView() {
        topicAdapter = CivicTopicAdapter(civicRepository.getCivicTopics()) { topic ->
            val guide = civicRepository.getGuidesForTopic(topic.id).firstOrNull()
                ?: civicRepository.getFeaturedGuide()
            openGuideDetail(guide)
        }

        binding.rvCivicTopics.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topicAdapter
        }
    }

    private fun openGuideDetail(guide: CivicGuide) {
        val intent = Intent(requireContext(), GuideDetailActivity::class.java).apply {
            putExtra(GuideDetailActivity.EXTRA_GUIDE, guide)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
