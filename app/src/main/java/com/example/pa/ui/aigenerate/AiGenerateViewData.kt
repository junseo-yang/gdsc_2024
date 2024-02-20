package com.example.pa.ui.aigenerate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pa.data.DatabaseRepository

// Implement the viewModelProvider.Factory
class AiGenerateViewData(private val repository: DatabaseRepository) : ViewModelProvider.Factory  {

    //Deliver to the ViewModel creator to create an instance and provide this instance when requested by the ViewModelProvider
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AiGenerateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AiGenerateViewModel(repository) as T
        }
        throw IllegalArgumentException("No correspond ViewModel")
    }
}