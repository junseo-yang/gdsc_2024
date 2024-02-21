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
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec

// Define the database with the classes of each table
// Assign a version for the database
@Database(entities = [Tasks::class], version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (
            from = 2,
            to = 3,
            spec = PlannerDatabase.SetAutoMigration::class
            )
    ]
)
@TypeConverters(LocalDateTimeConverters::class)
abstract class PlannerDatabase: RoomDatabase() {
    @DeleteColumn("tasks_table", "task_duration")
    class SetAutoMigration: AutoMigrationSpec

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
                )
                    //.fallbackToDestructiveMigration() // Only uncomment this line if you need to change the database without migration
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}