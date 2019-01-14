package com.mmteams91.todoapp.common.transform.fieldparser

import com.mmteams91.todoapp.common.entities.IItem

class ItemFieldParser : FieldParser<IItem, IItem> {
    override fun parseFields(from: IItem, to: IItem) {
        to.dayOrder = from.dayOrder
        to.assignedByUid = from.assignedByUid
        to.isArchived = from.isArchived
        to.labels = from.labels
        to.allDay = from.allDay
        to.inHistory = from.inHistory
        to.dateAdded = from.dateAdded
        to.indent = from.indent
        to.dateLang = from.dateLang
        to.id = from.id
        to.priority = from.priority
        to.checked = from.checked
        to.userId = from.userId
        to.dueDateUtc = from.dueDateUtc
        to.content = from.content
        to.parentId = from.parentId
        to.itemOrder = from.itemOrder
        to.isDeleted = from.isDeleted
        to.responsibleUid = from.responsibleUid
        to.projectId = from.projectId
        to.dateCompleted = from.dateCompleted
        to.collapsed = from.collapsed
        to.dateString = from.dateString
    }
}