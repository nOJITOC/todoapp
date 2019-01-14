package com.mmteams91.todoapp.common.entities

import com.mmteams91.todoapp.common.extensions.isNotDefault
import java.util.*

interface IItem : IBaseEntity{
    var dayOrder: Int
    var assignedByUid: Long?
    var isArchived: Int
    var labels: List<Long>
    var allDay: Boolean
    var inHistory: Int
    var dateAdded: Date
    var indent: Int
    var dateLang: String?
    var priority: Int
    var checked: Int
    var userId: Long
    var dueDateUtc: Date?
    var content: String
    var parentId: Long?
    var itemOrder: Int
    var isDeleted: Int
    var responsibleUid: Long?
    var projectId: Long
    var dateCompleted: Date?
    var collapsed: Int
    var dateString: String?

    fun isCollapsed() = collapsed.isNotDefault()

    fun isChecked() = checked.isNotDefault()

    fun isInHistory() = inHistory.isNotDefault()

    fun isDeleted() = isDeleted.isNotDefault()

    fun isArchived() = isArchived.isNotDefault()

}