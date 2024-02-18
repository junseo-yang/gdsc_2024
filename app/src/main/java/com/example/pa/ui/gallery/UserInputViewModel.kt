// This ViewModel is just in case
package com.example.pa.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInputViewModel  : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "placeholder"
    }
    val text: LiveData<String> = _text

    fun setDate(date: String) {
        _text.value = date
    }
}
