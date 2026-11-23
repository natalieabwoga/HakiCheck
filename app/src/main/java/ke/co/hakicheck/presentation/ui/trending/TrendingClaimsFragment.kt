package ke.co.hakicheck.presentation.ui.trending

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.hakicheck.HakiCheckApplication
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.FragmentTrendingClaimsBinding
import ke.co.hakicheck.domain.model.TrendingClaim
import ke.co.hakicheck.presentation.ui.verify.VerificationResultActivity

class TrendingClaimsFragment : Fragment() {

    private var _binding: FragmentTrendingClaimsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TrendingClaimsAdapter
    private var allClaims: List<TrendingClaim> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingClaimsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as HakiCheckApplication
        allClaims = app.factCheckRepository.getTrendingClaims()

        setupRecyclerView()
        setupFilters()
    }

    private fun setupRecyclerView() {
        adapter = TrendingClaimsAdapter(allClaims) { claim ->
            val intent = Intent(requireContext(), VerificationResultActivity::class.java).apply {
                putExtra(VerificationResultActivity.EXTRA_VERIFICATION, claim.verification)
            }
            startActivity(intent)
        }

        binding.rvTrendingClaims.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@TrendingClaimsFragment.adapter
        }
    }

    private fun setupFilters() {
        binding.chipFilterAll.setOnClickListener {
            selectFilter(binding.chipFilterAll)
            adapter.updateList(allClaims)
        }

        binding.chipFilterElections.setOnClickListener {
            selectFilter(binding.chipFilterElections)
            val filtered = allClaims.filter { it.verification.sourceCategory == "ELECTIONS" }
            adapter.updateList(filtered)
        }

        binding.chipFilterRights.setOnClickListener {
            selectFilter(binding.chipFilterRights)
            val filtered = allClaims.filter { it.verification.sourceCategory == "BILL_OF_RIGHTS" }
            adapter.updateList(filtered)
        }

        binding.chipFilterFees.setOnClickListener {
            selectFilter(binding.chipFilterFees)
            val filtered = allClaims.filter { it.verification.sourceCategory == "FEES" }
            adapter.updateList(filtered)
        }
    }

    private fun selectFilter(selectedView: TextView) {
        val chips = listOf(
            binding.chipFilterAll,
            binding.chipFilterElections,
            binding.chipFilterRights,
            binding.chipFilterFees
        )

        for (chip in chips) {
            if (chip == selectedView) {
                chip.setBackgroundResource(R.drawable.bg_chip_selected)
                chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_unselected)
                chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
