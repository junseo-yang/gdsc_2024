/*
 * Project     :     Solution Challenge GDSC 2024
 * Author      :
 * Class       :     PlannerDatabase
 * Description :     Abstract class to define the properties of the local database.
 *                      The database is created and managed using Room Library.
 *                      Room reference: https://developer.android.com/training/data-storage/room
 */

package com.example.pa.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define the database with the classes of each table
// Assign a version for the database
@Database(entities = [Tasks::class], version = 1, exportSchema = false)
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