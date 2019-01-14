package com.mmteams91.todoapp.common.data.database

import android.arch.persistence.room.TypeConverter
import com.mmteams91.todoapp.common.data.network.HttpDateAdapter
import java.util.*

class RoomTypeConverters {

    private val dateAdapter: HttpDateAdapter = HttpDateAdapter()
    @TypeConverter
    fun fromDateToString(date: Date?): String? = dateAdapter.toJson(date)


    @TypeConverter
    fun fromStringToDate(string: String?): Date? = dateAdapter.fromJson(string)

    companion object {
        private const val SEPARATOR = ","
    }

    @TypeConverter
    fun fromStringToLongList(valuesJoinedString: String?): List<Long> =
            valuesJoinedString?.split(SEPARATOR)?.asSequence()?.filter { it.isNotBlank() }?.map { it.toLong() }?.toList()
                    ?: listOf()


    @TypeConverter
    fun fromLongListToString(values: List<Long>): String? {
        val result = values.joinToString(SEPARATOR)
        return if (!result.isEmpty()) result else null
    }
}