package com.example.pa.ui.gallery

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pa.databinding.FragmentCreateManuallyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar


class UserInputFragment : Fragment() {

    // Declare a nullable variable to hold the ViewBinding object for this fragment
    private var _binding: FragmentCreateManuallyBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!  // Create a property to access the ViewBinding object with null-safety

    private lateinit var taskEditText: EditText
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout and initialize the ViewBinding object
        _binding = FragmentCreateManuallyBinding.inflate(inflater, container, false)
        val root: View =
            binding.root   // binding.root is used to access the root view of the layout associated with the ViewBinding object binding.
        // root is a variable of type View that holds the root view of the layout.

        // Initialize views
        taskEditText = binding.taskManualInput
        startDateEditText = binding.taskStartDate
        endDateEditText = binding.taskEndDate

        // Initialize Calender instance to get current date
        val calendar = Calendar.getInstance()
        val pickedStartDate = Calendar.getInstance()

        startDateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Get the current year, month, and day from the calendar
                val year = calendar[Calendar.YEAR]
                val month = calendar[Calendar.MONTH]
                val day = calendar[Calendar.DAY_OF_MONTH]

                // Create a new instance of DatePickerDialog and show it
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int -> // This method is called when a date is selected

                        // set the picked Start Date
                        pickedStartDate.set(Calendar.YEAR, selectedYear)
                        pickedStartDate.set(Calendar.MONTH, selectedMonth)
                        pickedStartDate.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

                        // Update the start date EditText to show the selected date
                        /* In Calendar class (and consequently in DatePicker), months are zero-based.
                                 This means January is 0 */
                        val pickedDate: String =
                            selectedYear.toString() + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth

                        startDateEditText.setText(pickedDate)

                    }, year, month, day
                )

                // Set the minimum selectable date to the current date (no past dates)
                datePickerDialog.datePicker.minDate = calendar.timeInMillis
                datePickerDialog.show()
            }
        }

        endDateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Get the current year, month, and day from the calendar
                val year = calendar[Calendar.YEAR]
                val month = calendar[Calendar.MONTH]
                val day = pickedStartDate[Calendar.DAY_OF_MONTH]

                // Create a new instance of DatePickerDialog and show it
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int -> // This method is called when a date is selected

                        // Update the end date EditText to show the selected date
                        val pickedDate: String =
                            selectedYear.toString() + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth

                        endDateEditText.setText(pickedDate)

                    }, year, month, day
                )

                // Set the minimum selectable date to the picked Start Date
                datePickerDialog.datePicker.minDate =
                    pickedStartDate.timeInMillis + 24 * 60 * 60 * 1000
                datePickerDialog.show()
            }
        }


        val buttonCreateTask =
            binding.btnCreateTask // Access the Button view using the binding object
        buttonCreateTask.setOnClickListener {
            // Code here executes on main thread after user presses button
            if (!validateInput()) {
                // Input is invalid, display error messages or take appropriate action
                // show a toast message indicating validation failed
                Toast.makeText(
                    requireContext(),
                    "Please fill in all required fields.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // show a toast message indicating task created
                Toast.makeText(
                    requireContext(),
                    "Congrats! A new task created!",
                    Toast.LENGTH_SHORT
                ).show()

                // TODO：call processInput() to insert data to database
                /*val databaseRepository = DatabaseRepository(PlannerDatabase.getDatabase(applicationContext).taskDao())
                processInput(databaseRepository)*/
            }

        }

        return root     // Return the root view of the fragment layout
    }


    private fun validateInput(): Boolean {
        // get input value
        val taskInput = taskEditText.text.toString()
        val startDate = startDateEditText.text.toString()
        val endDate = endDateEditText.text.toString()
        var status = true

        // validation
        if (taskInput.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            status = false
        }
        return status
    }

    // TODO：insert data to database
    /*private fun processInput(databaseRepository: DatabaseRepository) {

        // Create a new task object without specifying taskId
        val newTask = Tasks(
            taskInput = taskEditText.text.toString(), // Provide the task description
            startDatetime = startDateEditText.text.toString(), // Use current date and time
            endDate = endDateEditText.text.toString(),
            // taskDuration = 0, // Provide the task duration
            isTaskComplete = false // Indicate if the task is complete or not
        )

        // Start a coroutine to insert the task into the database
        CoroutineScope(Dispatchers.IO).launch {
            databaseRepository.insert(newTask)
        }
    }*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null     // Release the ViewBinding object to avoid memory leaks
    }
}