package com.example.pa.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tasks_table")
data class Tasks(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "task_id") val taskId: Int,
    @ColumnInfo(name = "task_description") val taskInput: String?,
    @ColumnInfo(name = "start_date_time") val startDate: LocalDate, //TODO: check SQLite data for datetime
    @ColumnInfo(name = "task_duration") val taskDuration: Int,
    @ColumnInfo(name = "task_complete") val isTaskComplete: Boolean
)


