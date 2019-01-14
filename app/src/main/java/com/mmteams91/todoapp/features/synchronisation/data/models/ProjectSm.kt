package com.mmteams91.todoapp.features.synchronisation.data.models

import com.mmteams91.todoapp.common.entities.IProject
import com.squareup.moshi.Json

data class ProjectSm(
        @Json(name = "id")
        override var id: Long,
        @Json(name = "name")
        override var name: String,
        @Json(name = "color")
        override var color: Int,
        @Json(name = "indent")
        override var indent: Int,
        @Json(name = "item_order")
        override var itemOrder: Int,
        @Json(name = "collapsed")
        override var collapsed: Int,
        @Json(name = "shared")
        override var shared: Boolean,
        @Json(name = "is_deleted")
        override var isDeleted: Int,
        @Json(name = "is_archived")
        override var isArchived: Int,
        @Json(name = "is_favorite")
        override var isFavorite: Int,
        @Json(name = "inbox_project")
        override var inboxProject: Boolean = false,
        @Json(name = "team_inbox")
        override var teamInbox: Boolean? = null,
        override var parentId: Long?
) : IProject