package ke.co.hakicheck.presentation.ui.verify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ke.co.hakicheck.databinding.ItemEvidenceSourceBinding
import ke.co.hakicheck.domain.model.EvidenceSource

class EvidenceSourceAdapter(
    private var sources: List<EvidenceSource> = emptyList(),
    private val onItemClick: ((EvidenceSource) -> Unit)? = null
) : RecyclerView.Adapter<EvidenceSourceAdapter.EvidenceViewHolder>() {

    fun updateList(newSources: List<EvidenceSource>) {
        sources = newSources
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvidenceViewHolder {
        val binding = ItemEvidenceSourceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EvidenceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EvidenceViewHolder, position: Int) {
        holder.bind(sources[position])
    }

    override fun getItemCount(): Int = sources.size

    inner class EvidenceViewHolder(private val binding: ItemEvidenceSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: EvidenceSource) {
            binding.tvEvidenceTitle.text = source.title
            binding.tvDocTypePill.text = source.documentType.uppercase()
            binding.tvEvidenceSnippet.text = source.extractSnippet

            binding.root.setOnClickListener {
                onItemClick?.invoke(source)
            }
        }
    }
}
