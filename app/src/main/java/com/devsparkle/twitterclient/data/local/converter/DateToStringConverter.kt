package com.devsparkle.twitterclient.data.local.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
/**
 * Type converters to allow Room to reference complex data types.
 */
class DateToStringConverter {
    @TypeConverter
    fun dateToString(date: Date): String {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            return sdf.format(date)
        } catch (e: Exception) {
            return ""
        }
    }

    @TypeConverter
    fun stringToDate(value: String): Date? {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            return sdf.parse(value)
        } catch (e: Exception) {
            return null
        }
    }
}