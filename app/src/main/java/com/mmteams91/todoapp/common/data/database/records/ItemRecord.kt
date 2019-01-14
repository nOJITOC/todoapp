package com.mmteams91.todoapp.common.data.database.records

import android.arch.persistence.room.Entity
import com.mmteams91.todoapp.common.data.database.ITEMS
import com.mmteams91.todoapp.common.data.database.NO_ID
import com.mmteams91.todoapp.common.entities.IItem
import com.mmteams91.todoapp.common.extensions.EMPTY
import com.mmteams91.todoapp.common.utils.currentDate
import java.util.*

@Entity(tableName = ITEMS)
class ItemRecord : BaseRecord(), IItem {
    override var dayOrder: Int = -1
    override var assignedByUid: Long? = NO_ID
    override var isArchived: Int = 0
    override var labels: List<Long> = emptyList()
    override var allDay: Boolean = false
    override var inHistory: Int = 0
    override var dateAdded: Date = currentDate()
    override var indent: Int = 1
    override var dateLang: String? = null
    override var priority: Int = 1
    override var checked: Int = 0
    override var userId: Long = NO_ID
    override var dueDateUtc: Date? = null
    override var content: String = String.EMPTY
    override var parentId: Long? = null
    override var itemOrder: Int = 1
    override var isDeleted: Int = 0
    override var responsibleUid: Long? = null
    override var projectId: Long = NO_ID
    override var dateCompleted: Date? = null
    override var collapsed: Int = 0
    override var dateString: String? = null
}