package com.mmteams91.todoapp.common.data.database.records

import android.arch.persistence.room.Entity
import com.mmteams91.todoapp.common.data.database.PROJECTS
import com.mmteams91.todoapp.common.entities.IProject
import com.mmteams91.todoapp.common.extensions.EMPTY

@Entity(tableName = PROJECTS)
class ProjectRecord : BaseRecord(), IProject {
    override var parentId: Long? = null
    override var name: String = String.EMPTY
    override var color: Int = 21
    override var indent: Int = 1
    override var itemOrder: Int = 1
    override var collapsed: Int = 0
    override var shared: Boolean = false
    override var isDeleted: Int = 0
    override var isArchived: Int = 0
    override var isFavorite: Int = 0
    override var inboxProject: Boolean = true
    override var teamInbox: Boolean? = null
}