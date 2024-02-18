package com.example.pa.ui.gallery

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pa.R
import com.example.pa.databinding.FragmentUserInputBinding
import java.util.Calendar


class UserInputFragment  : Fragment() {

    // Declare a nullable variable to hold the ViewBinding object for this fragment
    private var _binding: FragmentUserInputBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!  // Create a property to access the ViewBinding object with null-safety

    private var pickedYear = 0
    private var pickedMonth = 0
    private var pickedDay = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val userInputViewModel =
            ViewModelProvider(this)[UserInputViewModel::class.java]*/

        // Inflate the layout and initialize the ViewBinding object
        _binding = FragmentUserInputBinding.inflate(inflater, container, false)
        val root: View = binding.root   // binding.root is used to access the root view of the layout associated with the ViewBinding object binding.
                                        // root is a variable of type View that holds the root view of the layout.

        /*val textView: TextView = binding.textUserInput
        userInputViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        // Initialize Calender instance to get current date
        val calendar = Calendar.getInstance()
        val pickedDate = Calendar.getInstance()

        val date = binding.labelUserInput
        date.text = UserInputViewModel().text.value
//        date.setText(UserInputViewModel().text)
        val textView: TextView = binding.labelUserInput
        UserInputViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val pickTime = binding.pickTime
        pickTime.setOnClickListener{
            TimePickerFragment().show(parentFragmentManager, "timePicker")
        }

        val pickDate = binding.pickDate

        pickDate.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(parentFragmentManager, "datePicker")
        }


        val taskDeadline = binding.taskDeadline    // Access the EditText view using the binding object
        taskDeadline.setOnClickListener {

                fun onClick(v: View?) {

                    // Get the current year, month, and day from the calendar
                    val year = calendar[Calendar.YEAR]
                    val month = calendar[Calendar.MONTH]
                    val day = calendar[Calendar.DAY_OF_MONTH]

                    // Create a new instance of DatePickerDialog and show it
                    val datePickerDialog = DatePickerDialog(requireContext(),
                        { view, year, month, dayOfMonth -> // This method is called when a date is selected

                            // set the picked Departure Date
                            pickedDate.set(Calendar.YEAR, year)
                            pickedDate.set(Calendar.MONTH, month)
                            pickedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            pickedYear = year
                            pickedMonth = month
                            pickedDay = dayOfMonth

                            // Update the pickDateButton text to show the selected date
                            /* In Java's Calendar class (and consequently in DatePicker), months are zero-based.
                                     This means January is 0 */
                            val pickedStartDate: String =
                                pickedDay.toString() + "/" + (pickedMonth + 1) + "/" + pickedYear
                            taskDeadline.setText(pickedStartDate)
                        }, year, month, day
                    )

                    // Set the minimum selectable date to the current date (no past dates)
                    datePickerDialog.datePicker.minDate = calendar.timeInMillis
                    datePickerDialog.show()
                }
        }


        val buttonCreateTask = binding.btnCreateTask // Access the Button view using the binding object // val buttonNotPaid: Button = binding.btnNotPaid
        buttonCreateTask.setOnClickListener {
            // Code here executes on main thread after user presses button
            // TODO：come back, change action id
//            findNavController().navigate(R.id.action_gallery_to_slideshow)
        }

        return root     // Return the root view of the fragment layout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null     // Release the ViewBinding object to avoid memory leaks
    }
}