package com.example.pa.ui.gallery

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.pa.R
import com.example.pa.databinding.FragmentUserInputBinding
import java.util.Calendar


// Define the interface for handling date and time selection
interface DateTimeSelectedListener {
    fun onDateTimeSelected(dateTime: Calendar)
}

// DatePickerFragment for selecting a date
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    // Declare a nullable variable to hold the ViewBinding object for this fragment
    //private var _binding: DialogFragmentDatePickerBinding? = null
    private var _binding: FragmentUserInputBinding? = null

    private var listener: DateTimeSelectedListener? = null

    fun setListener(listener: DateTimeSelectedListener) {
        this.listener = listener
    }

//    val taskDeadline = findViewById<EditText>(R.id.task_deadline)
//    val taskDeadline = findViewById<EditText>(R.id.taskDeadline)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)

        // set the minimum date to today
        datePickerDialog.datePicker.minDate = c.timeInMillis

        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        // Handle date selection and notify the listener
        val calendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }
        val viewModel = UserInputViewModel()
        viewModel.setDate(calendar.toString())
//        listener?.onDateTimeSelected(calendar)
    }
}

// TimePickerFragment for selecting a time
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var listener: DateTimeSelectedListener? = null

    fun setListener(listener: DateTimeSelectedListener) {
        this.listener = listener
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker.
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it.
        return TimePickerDialog(
            requireContext(),
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Handle time selection and notify the listener
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }



        listener?.onDateTimeSelected(calendar)
    }
}


