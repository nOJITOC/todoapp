package com.mmteams91.todoapp.common.data.database.records

import android.arch.persistence.room.PrimaryKey
import com.mmteams91.todoapp.common.data.database.NO_ID
import com.mmteams91.todoapp.common.entities.IBaseEntity

abstract class BaseRecord : IBaseEntity {
    @PrimaryKey
    var localId: Long = NO_ID
    override var id: Long = NO_ID
}