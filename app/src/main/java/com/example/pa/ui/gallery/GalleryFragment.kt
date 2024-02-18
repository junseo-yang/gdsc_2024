package com.example.pa.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pa.R
import com.example.pa.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this)[GalleryViewModel::class.java]

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val buttonNotPaid =
            view?.findViewById<Button>(R.id.btn_not_paid)
        buttonNotPaid?.setOnClickListener {
            // Code here executes on main thread after user presses button
            // TODO：come back to check
//            findNavController().navigate(R.id.action_gallery_to_user_input)
        }
        val buttonPaid = view?.findViewById<Button>(R.id.btn_paid)
        buttonPaid?.setOnClickListener {
            // Code here executes on main thread after user presses button
            // TODO：come back to check
//            findNavController().navigate(R.id.action_gallery_to_slideshow)  // or navigate to the AI fragment?
        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}