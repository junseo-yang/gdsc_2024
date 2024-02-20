package com.example.pa.ui.aigenerate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pa.R
import com.example.pa.data.DatabaseRepository
import com.example.pa.data.local.PlannerDatabase
import com.example.pa.databinding.FragmentAiGenerateBinding

class AiGenerateFragment : Fragment() {

    private var _binding: FragmentAiGenerateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AiGenerateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        restorePreviousState: Bundle?
    ): View {
        val database = PlannerDatabase.getDatabase(requireContext())
        val repository = DatabaseRepository(database.taskDao())
        val factory = AiGenerateViewData(repository)

        viewModel = ViewModelProvider(this, factory).get(AiGenerateViewModel::class.java)


        _binding = FragmentAiGenerateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) { responseText ->
            binding.aiGenerateResponse.text = responseText
        }

        binding.aiGenerateButton.setOnClickListener {
            val topicInput = binding.aiGenerateTopicInput.text.toString()
            val dateInput = binding.aiGenerateDateInput.text.toString()
            val durationInput = binding.aiGenerateDurationInput.text.toString()

            if (topicInput.isNotEmpty() && dateInput.isNotEmpty() && durationInput.isNotEmpty()) {
                val durationInt = durationInput.filter { it.isDigit() }.toIntOrNull() ?: 0
                viewModel.fetchChatCompletion(topicInput, dateInput, durationInt)
                viewModel.insertTask(topicInput, dateInput, durationInt)

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