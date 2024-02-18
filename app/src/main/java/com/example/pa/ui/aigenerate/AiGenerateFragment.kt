package com.example.pa.ui.aigenerate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pa.R
import com.example.pa.databinding.FragmentAiGenerateBinding

class AiGenerateFragment : Fragment() {

    private var _binding: FragmentAiGenerateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(AiGenerateViewModel::class.java)

        _binding = FragmentAiGenerateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) { responseText ->
            binding.aiGenerateResponse.text = responseText
        }

        binding.aiGenerateButton.setOnClickListener {
            val userInput = binding.aiGenerateTopicInput.text.toString()
            if (userInput.isNotEmpty()) {
                viewModel.fetchChatCompletion(userInput)
            } else {
                binding.aiGenerateResponse.text = getString(R.string.ai_generate_topic_input_hint)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}