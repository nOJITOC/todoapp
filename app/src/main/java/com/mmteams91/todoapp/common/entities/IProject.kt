package com.mmteams91.todoapp.common.entities

import com.mmteams91.todoapp.common.extensions.isNotDefault

interface IProject:IBaseEntity {
    var parentId: Long?
    var name: String
    var color: Int
    var indent: Int
    var itemOrder: Int
    var collapsed: Int
    var shared: Boolean
    var isDeleted: Int
    var isArchived: Int
    var isFavorite: Int
    var inboxProject: Boolean
    var teamInbox: Boolean?

    fun isDeleted() = isDeleted.isNotDefault()

    fun isArchived() = isArchived.isNotDefault()

    fun isFavorite() = isFavorite.isNotDefault()

}