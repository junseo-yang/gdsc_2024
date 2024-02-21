package com.example.pa.ui.aigenerate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        savedInstanceState: Bundle?
    ): View {
        // Initialize the database and repository
        val database = PlannerDatabase.getDatabase(requireContext())
        val repository = DatabaseRepository(database.taskDao())

        // Use ViewModelProvider to instantiate the ViewModel
        viewModel = ViewModelProvider(this, AiGenerateViewData(repository))
            .get(AiGenerateViewModel::class.java)

        viewModel.text.observe(viewLifecycleOwner) { responseText ->
            binding.aiGenerateResponse.text = responseText
        }

        // Inflate the layout for this fragment
        _binding = FragmentAiGenerateBinding.inflate(inflater, container, false)



        // Return the root view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner) { responseText ->
            binding.aiGenerateResponse.text = responseText
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clean up the binding when the view is destroyed
    }
}
