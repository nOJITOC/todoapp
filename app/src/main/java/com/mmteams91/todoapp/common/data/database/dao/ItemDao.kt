package com.mmteams91.todoapp.common.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mmteams91.todoapp.common.data.database.records.ItemRecord

@Dao
interface ItemDao:BaseDao<ItemRecord> {

    @Query("SELECT * FROM items where id in (:ids)")
    override fun getByIds(ids: LongArray): List<ItemRecord>
}