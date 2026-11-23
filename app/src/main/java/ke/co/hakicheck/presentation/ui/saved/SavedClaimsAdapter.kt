package ke.co.hakicheck.presentation.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ke.co.hakicheck.databinding.ItemSavedClaimBinding
import ke.co.hakicheck.domain.model.ClaimVerification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SavedClaimsAdapter(
    private var items: List<ClaimVerification> = emptyList(),
    private val onItemClick: (ClaimVerification) -> Unit,
    private val onDeleteClick: (ClaimVerification) -> Unit
) : RecyclerView.Adapter<SavedClaimsAdapter.SavedViewHolder>() {

    fun updateList(newItems: List<ClaimVerification>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val binding = ItemSavedClaimBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SavedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class SavedViewHolder(private val binding: ItemSavedClaimBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ClaimVerification) {
            val context = binding.root.context
            binding.tvSavedClaim.text = item.claimText
            binding.tvSavedSummary.text = item.summary

            val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
            binding.tvSavedDate.text = "Verified ${dateFormat.format(Date(item.checkedAtMillis))}"
            binding.tvSavedConfidence.text = "${item.confidenceScore}% confidence"

            val verdict = item.verdict
            binding.tvSavedBadge.setText(verdict.titleRes)
            binding.tvSavedBadge.setBackgroundResource(verdict.badgeBackground)
            binding.tvSavedBadge.setTextColor(ContextCompat.getColor(context, verdict.badgeTextColor))

            binding.root.setOnClickListener {
                onItemClick(item)
            }

            binding.btnDeleteSaved.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }
}
