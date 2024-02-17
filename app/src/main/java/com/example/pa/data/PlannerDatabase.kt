package com.example.pa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Tasks::class), version = 1, exportSchema = false)
public abstract class PlannerDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    // Singleton to instantiate the database
    companion object {
        @Volatile
        private var INSTANCE: PlannerDatabase? = null

        // Create database
        fun getDatabase(context: Context): PlannerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlannerDatabase::class.java,
                    "planner_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}