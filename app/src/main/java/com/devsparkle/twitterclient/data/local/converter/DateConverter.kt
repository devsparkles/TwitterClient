package com.devsparkle.twitterclient.data.local.converter

import androidx.room.TypeConverter
import java.util.Calendar
/**
 * Type converters to allow Room to reference complex data types.
 */
class DateConverter {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}