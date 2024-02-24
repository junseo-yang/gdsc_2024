package com.example.pa.ui.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pa.data.DatabaseRepository
import com.example.pa.data.local.Tasks
import kotlinx.coroutines.launch

class TasksListViewModel(private val repository: DatabaseRepository): ViewModel() {
    // Use LiveData to return the tasks
    val allTasks: LiveData<List<Tasks>> = repository.allTasks.asLiveData()

    fun insertTask(task: Tasks) = viewModelScope.launch {
        repository.insert(task)
    }
}

class TasksListViewModelFactory(private val repository: DatabaseRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}