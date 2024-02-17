/*
 * Project     :     Solution Challenge GDSC 2024
 * Author      :
 * Class       :     TaskDao
 * Description :     Implements an interface to manage the table tasks_table in the local database.
 *                      Room Library controls the insert, delete, update and queries.
 *                      Room reference: https://developer.android.com/training/data-storage/room
 */

package com.example.pa.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query ("SELECT * FROM tasks_table ORDER BY start_date_time")
    fun getAllTasks(): Flow<List<Tasks>>

    @Insert
    suspend fun insert(task: Tasks)

    @Delete
    suspend fun delete(task: Tasks)

    @Update
    suspend fun update(task: Tasks)
}