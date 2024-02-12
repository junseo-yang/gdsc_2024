package com.example.pa.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    //TODO: If there's time, instead of "Welcome", we can retrieve a random "Affirmation" sentence from the database
    // userDatabase(we can have a place for user to enter and build their own Affirmations database,
    // words they want to keep reminding themselves, e.g. "Let's start from small", "You don't have to be perfect");
    // Or: retrieve from a general affirmation database we build


    /*  MutableLiveData: a class from the Android Jetpack library commonly used for holding and observing data in Android applications.
                        It's mutable, meaning its value can be changed.
        LiveData: Also from the Android Jetpack library, LiveData is an observable data holder class.
                  It's used for propagating changes to UI components in a lifecycle-aware manner.
    */
    private val _text = MutableLiveData<String>().apply {
        value = "Welcome"
    }
    val text: LiveData<String> = _text
}