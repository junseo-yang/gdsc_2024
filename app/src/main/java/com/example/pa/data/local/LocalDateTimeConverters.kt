package com.example.pa.data.local
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateTimeConverters {

    @TypeConverter
    fun changeString(value: String?): LocalDate? {
        return value?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

    @TypeConverter
    fun changeLocalDate(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}