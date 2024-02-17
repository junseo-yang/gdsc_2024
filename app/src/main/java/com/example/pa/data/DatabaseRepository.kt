/*
 * Project     :     Solution Challenge GDSC 2024
 * Author      :
 * Class       :     DatabaseRespository
 * Description :     The repository class exposes the data to the rest of the application.
 *                      It ensures the UI layer won't have direct access to the database.
 *                      Reference: https://developer.android.com/topic/architecture#fetching_data
 */
package com.example.pa.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val taskDao: TaskDao) {

    // Observed Flow will notify the observer when there's a change
    val allTasks: Flow<List<Tasks>> = taskDao.getAllTasks()

    // Suspended queries run in a different thread of the main thread
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Tasks) {
        taskDao.insert(task)
    }
}