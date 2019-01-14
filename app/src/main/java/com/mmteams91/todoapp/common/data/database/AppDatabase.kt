package com.mmteams91.todoapp.common.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.mmteams91.todoapp.common.data.database.dao.ItemDao
import com.mmteams91.todoapp.common.data.database.dao.ProjectDao
import com.mmteams91.todoapp.common.data.database.records.ItemRecord
import com.mmteams91.todoapp.common.data.database.records.ProjectRecord

@Database(entities = [
    ItemRecord::class,
    ProjectRecord::class
], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun getItemDao():ItemDao

    abstract fun getProjectDao():ProjectDao
}