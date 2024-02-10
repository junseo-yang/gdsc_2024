package com.example.pa.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pa.databinding.FragmentHomeBinding
import com.example.pa.ui.gallery.GalleryFragment


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val buttonNotPaid: Button = binding.btnNotPaid
        buttonNotPaid.setOnClickListener {
            // Code here executes on main thread after user presses button
            // TODO：come back
            findNavController().navigate(com.example.pa.R.id.fragment_gallery)
        }
        val buttonPaid: Button = binding.btnNotPaid
        buttonPaid.setOnClickListener {
            // Code here executes on main thread after user presses button
            // TODO：come back
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}