package ke.co.hakicheck.presentation.ui.knowledge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.ItemCivicCardBinding
import ke.co.hakicheck.domain.model.CivicTopic

class CivicTopicAdapter(
    private var topics: List<CivicTopic> = emptyList(),
    private val onTopicClick: (CivicTopic) -> Unit
) : RecyclerView.Adapter<CivicTopicAdapter.TopicViewHolder>() {

    fun updateList(newTopics: List<CivicTopic>) {
        topics = newTopics
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemCivicCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(topics[position])
    }

    override fun getItemCount(): Int = topics.size

    inner class TopicViewHolder(private val binding: ItemCivicCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: CivicTopic) {
            binding.tvTopicTitle.text = topic.title
            binding.tvTopicDesc.text = topic.description
            binding.tvTopicLegalBasis.text = topic.legalBasis

            // Map icon
            val iconRes = when (topic.iconName) {
                "ic_verdict_true" -> R.drawable.ic_verdict_true
                "ic_scale_justice" -> R.drawable.ic_scale_justice
                "ic_info" -> R.drawable.ic_info
                else -> R.drawable.ic_library_books
            }
            binding.ivTopicIcon.setImageResource(iconRes)

            binding.root.setOnClickListener {
                onTopicClick(topic)
            }
        }
    }
}
