package ke.co.hakicheck.presentation.ui.verify

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ke.co.hakicheck.databinding.BottomSheetCitationBinding
import ke.co.hakicheck.domain.model.EvidenceSource

class CitationDetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCitationBinding? = null
    private val binding get() = _binding!!

    private var source: EvidenceSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        source = arguments?.getSerializable(ARG_SOURCE) as? EvidenceSource
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCitationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        source?.let { s ->
            binding.tvSheetDocType.text = s.documentType.uppercase()
            binding.tvSheetTitle.text = s.title
            binding.tvSheetCitation.text = "${s.legalCitation} • Laws of Kenya"
            binding.tvSheetExtract.text = "“${s.extractSnippet}”"

            binding.btnCopyCitation.setOnClickListener {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val text = "${s.title} (${s.legalCitation}):\n\n“${s.extractSnippet}”"
                clipboard.setPrimaryClip(ClipData.newPlainText("Legal Citation", text))
                Toast.makeText(requireContext(), "Legal extract copied to clipboard.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCloseSheet.setOnClickListener { dismiss() }
        binding.btnDismissSheet.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SOURCE = "arg_source"

        fun newInstance(source: EvidenceSource): CitationDetailBottomSheet {
            return CitationDetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_SOURCE, source)
                }
            }
        }
    }
}
