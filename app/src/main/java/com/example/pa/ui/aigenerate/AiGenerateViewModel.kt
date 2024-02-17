package com.example.pa.ui.aigenerate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AiGenerateViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is aigenerate Fragment"
    }
    val text: LiveData<String> = _text
}