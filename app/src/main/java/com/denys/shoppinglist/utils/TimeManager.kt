package com.denys.shoppinglist.utils

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeManager {
    private const val DEF_TIME_FORMAT = "hh:mm:ss — yyyy/MM/dd"
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    fun getTimeFormat(time: String, defPreferences: SharedPreferences): String {
        val defaultFormatter = SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        val defaultDate = defaultFormatter.parse(time)
        val newFormat = defPreferences.getString("time_format_key", DEF_TIME_FORMAT)
        val newFormatter = SimpleDateFormat(newFormat, Locale.getDefault())
        return if (defaultDate != null) {
            newFormatter.format(defaultDate)
        } else {
            time
        }
    }
}