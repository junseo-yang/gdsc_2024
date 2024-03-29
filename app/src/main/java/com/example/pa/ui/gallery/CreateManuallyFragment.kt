package com.example.pa.ui.gallery

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pa.data.DatabaseRepository
import com.example.pa.data.local.PlannerDatabase
import com.example.pa.data.local.Tasks
import com.example.pa.databinding.FragmentCreateManuallyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


class CreateManuallyFragment : Fragment() {

    // Declare a nullable variable to hold the ViewBinding object for this fragment
    private var _binding: FragmentCreateManuallyBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!  // Create a property to access the ViewBinding object with null-safety

    private var endDateIsSet: Boolean = false
    private lateinit var taskEditText: EditText
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText

    // Initialize Calender instance to get current date
    private lateinit var calendar: Calendar
    private lateinit var pickedStartDate: Calendar
    private lateinit var pickedEndDate: Calendar

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

        calendar = Calendar.getInstance()
        pickedStartDate = Calendar.getInstance()
        pickedEndDate = Calendar.getInstance()

        startDateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Get the current year, month, and day from the calendar
                val year = calendar[Calendar.YEAR]
                val month = calendar[Calendar.MONTH]
                val day = calendar[Calendar.DAY_OF_MONTH]

                // Create a new instance of DatePickerDialog and show it
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int -> // This method is called when a date is selected

                        // set the picked Start Date
                        pickedStartDate.set(Calendar.YEAR, selectedYear)
                        pickedStartDate.set(Calendar.MONTH, selectedMonth)
                        pickedStartDate.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

                        // Update the start date EditText to show the selected date
                        /* In Calendar class (and consequently in DatePicker), months are zero-based. January is 0 */
                        val pickedDate = String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDayOfMonth)
                        startDateEditText.setText(pickedDate)

                    }, year, month, day
                )

                // Set the minimum selectable date to the current date (no past dates)
                datePickerDialog.datePicker.minDate = calendar.timeInMillis

                if (endDateIsSet) {
                    datePickerDialog.datePicker.maxDate = pickedEndDate.timeInMillis
                }

                datePickerDialog.show()
            }
        }

        endDateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Get the start date's year, month, and day
                val year = pickedStartDate[Calendar.YEAR]
                val month = pickedStartDate[Calendar.MONTH]
                val day = pickedStartDate[Calendar.DAY_OF_MONTH]

                // Create a new instance of DatePickerDialog and show it
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int -> // This method is called when a date is selected

                        endDateIsSet = true // indicate the end date is set

                        // set the picked Start Date
                        pickedEndDate.set(Calendar.YEAR, selectedYear)
                        pickedEndDate.set(Calendar.MONTH, selectedMonth)
                        pickedEndDate.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

                        // Update the end date EditText to show the selected date
                        // In Calendar class (and consequently in DatePicker), months are zero-based. January is 0
                        val pickedDate = String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDayOfMonth)
                        endDateEditText.setText(pickedDate)

                    }, year, month, day
                )

                // Set the minimum selectable date to the picked Start Date
                datePickerDialog.datePicker.minDate =
                    pickedStartDate.timeInMillis + 24 * 60 * 60 * 1000
                datePickerDialog.show()
            }
        }

        val databaseRepository = DatabaseRepository(PlannerDatabase.getDatabase(requireContext().applicationContext).taskDao())

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


                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmation")
                builder.setMessage("Please confirm the task")

                // Set up the buttons
                builder.setPositiveButton("Confirm") { _, _ ->
                    // User clicked Yes button
                    // Call processInput() to insert data to database
                    processInput(databaseRepository)

                    // show a toast message indicating task created
                    Toast.makeText(
                        requireContext(),
                        "Congrats! A new task created!",
                        Toast.LENGTH_SHORT
                    ).show()

                    reset()    // clean inputs
                }

                builder.setNegativeButton("Back") { _, _ ->
                    // Do nothing when the user clicks No
                }

                // Create and show the dialog
                val dialog = builder.create()
                dialog.show()
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


    // Insert data to database
    private fun processInput(databaseRepository: DatabaseRepository) {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startDateInput = LocalDate.parse(startDateEditText.text.toString(), formatter)
        val endDateInput = LocalDate.parse(endDateEditText.text.toString(), formatter)

        // Create a new task object without specifying taskId
        val newTask = Tasks(
            taskInput = taskEditText.text.toString(), // Provide the task description
            startDate = startDateInput,// Use current date and time
            endDate = endDateInput,
            isTaskComplete = false, // Indicate if the task is complete or not
            taskId = 0,
        )

        // Start a coroutine to insert the task into the database
        CoroutineScope(Dispatchers.IO).launch {
            databaseRepository.insert(newTask)
        }
    }

    private fun reset() {
        taskEditText.text.clear()
        startDateEditText.text.clear()
        endDateEditText.text.clear()

        calendar = Calendar.getInstance()
        pickedStartDate = Calendar.getInstance()
        pickedEndDate = Calendar.getInstance()
        endDateIsSet = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null     // Release the ViewBinding object to avoid memory leaks
    }
}