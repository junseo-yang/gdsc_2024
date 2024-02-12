package com.example.pa.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pa.R

import com.example.pa.databinding.FragmentUserInputBinding


class UserInputFragment  : Fragment() {

    private var _binding: FragmentUserInputBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userInputViewModel =
            ViewModelProvider(this)[UserInputViewModel::class.java]

        _binding = FragmentUserInputBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textUserInput
        userInputViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        val buttonCreateTask =
            view?.findViewById<Button>(R.id.btn_create_task) // val buttonNotPaid: Button = binding.btnNotPaid
        buttonCreateTask?.setOnClickListener {
            // Code here executes on main thread after user presses button
            // TODO：come back, change action id
            findNavController().navigate(R.id.action_gallery_to_slideshow)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}