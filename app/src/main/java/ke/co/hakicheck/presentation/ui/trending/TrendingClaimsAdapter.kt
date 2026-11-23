package ke.co.hakicheck.presentation.ui.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ke.co.hakicheck.databinding.ItemTrendingClaimBinding
import ke.co.hakicheck.domain.model.TrendingClaim

class TrendingClaimsAdapter(
    private var claims: List<TrendingClaim> = emptyList(),
    private val onClaimClick: (TrendingClaim) -> Unit
) : RecyclerView.Adapter<TrendingClaimsAdapter.TrendingViewHolder>() {

    fun updateList(newClaims: List<TrendingClaim>) {
        claims = newClaims
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding = ItemTrendingClaimBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(claims[position])
    }

    override fun getItemCount(): Int = claims.size

    inner class TrendingViewHolder(private val binding: ItemTrendingClaimBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrendingClaim) {
            val context = binding.root.context
            binding.tvTrendingTitle.text = item.claimTitle
            binding.tvTrendingSummary.text = item.summary
            binding.tvViralCount.text = "${item.viralCount} reports"

            // Verdict badge
            val verdict = item.verdict
            binding.tvTrendingBadge.setText(verdict.titleRes)
            binding.tvTrendingBadge.setBackgroundResource(verdict.badgeBackground)
            binding.tvTrendingBadge.setTextColor(ContextCompat.getColor(context, verdict.badgeTextColor))

            binding.root.setOnClickListener {
                onClaimClick(item)
            }
        }
    }
}
