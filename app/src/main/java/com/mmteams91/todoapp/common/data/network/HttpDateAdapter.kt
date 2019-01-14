package com.mmteams91.todoapp.common.data.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import okhttp3.internal.Util.UTC
import java.text.SimpleDateFormat
import java.util.*

class HttpDateAdapter {

    private val dateFormat = SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss z", Locale.US).apply {
        timeZone = UTC
    }

    @FromJson
    fun fromJson(json: String?): Date? = json?.let { dateFormat.parse(it) }


    @ToJson
    fun toJson(date: Date?): String? = date?.let { dateFormat.format(it) }

}
private const val DATE_FORMAT_STRING = "Fri 26 Sep 2014 08:25:05 +0000"
