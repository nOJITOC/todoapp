package com.mmteams91.todoapp.common.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mmteams91.todoapp.common.data.database.records.ProjectRecord

@Dao
interface ProjectDao:BaseDao<ProjectRecord> {
    @Query("SELECT * FROM projects where id in (:ids)")
    override fun getByIds(ids: LongArray): List<ProjectRecord>
}