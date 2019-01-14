package com.mmteams91.todoapp.common.data.database.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Update
import com.mmteams91.todoapp.common.data.database.records.BaseRecord

interface BaseDao<T : BaseRecord> {

    @Insert(onConflict = REPLACE)
    fun insert(vararg entities: T)

    @Update(onConflict = REPLACE)
    fun update(vararg entities: T)

    fun getByIds(ids: LongArray): List<T>
    
}