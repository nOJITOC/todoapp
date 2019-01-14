package com.mmteams91.todoapp.common.transform.fieldparser

import com.mmteams91.todoapp.common.entities.IProject

class ProjectFieldParser : FieldParser<IProject, IProject> {
    override fun parseFields(from: IProject, to: IProject) {
        to.id = from.id
        to.parentId = from.parentId
        to.name = from.name
        to.color = from.color
        to.indent = from.indent
        to.itemOrder = from.itemOrder
        to.collapsed = from.collapsed
        to.shared = from.shared
        to.isDeleted = from.isDeleted
        to.isArchived = from.isArchived
        to.isFavorite = from.isFavorite
        to.inboxProject = from.inboxProject
        to.teamInbox = from.teamInbox
    }
}