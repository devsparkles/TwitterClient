package com.devsparkle.twitterclient.utils.extensions

import timber.log.Timber
import java.text.ParseException
import java.util.Calendar
import java.util.Date
import java.util.TimeZone



fun Date.addSeconds(seconds: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.SECOND, seconds)
    return cal.time
}

fun Date.addHours(hours: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.HOUR_OF_DAY, hours)
    return cal.time
}

fun Date.IsFutureDate(): Boolean {
    try {
        val tz: TimeZone = TimeZone.getTimeZone("UTC")
        val now = Calendar.getInstance(tz)
        return  this.after(now.time)
    } catch (e: ParseException) {
        e.printStackTrace()
        Timber.e(e)
        return false
    }
}

