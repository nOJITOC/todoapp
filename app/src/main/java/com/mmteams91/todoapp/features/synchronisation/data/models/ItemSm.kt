package com.mmteams91.todoapp.features.synchronisation.data.models

import com.mmteams91.todoapp.common.entities.IItem
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable
import java.util.Date

@JsonSerializable
data class ItemSm(
        @Json(name = "day_order")
        override var dayOrder: Int,
        @Json(name = "assigned_by_uid")
        override var assignedByUid: Long?,
        @Json(name = "is_archived")
        override var isArchived: Int,
        @Json(name = "labels")
        override var labels: List<Long>,
        @Json(name = "all_day")
        override var allDay: Boolean,
        @Json(name = "in_history")
        override var inHistory: Int,
        @Json(name = "date_added")
        override var dateAdded: Date,
        @Json(name = "indent")
        override var indent: Int,
        @Json(name = "date_lang")
        override var dateLang: String?,
        @Json(name = "id")
        override var id: Long,
        @Json(name = "priority")
        override var priority: Int,
        @Json(name = "checked")
        override var checked: Int,
        @Json(name = "user_id")
        override var userId: Long,
        @Json(name = "due_date_utc")
        override var dueDateUtc: Date?,
        @Json(name = "content")
        override var content: String,
        @Json(name = "parent_id")
        override var parentId: Long?,
        @Json(name = "item_order")
        override var itemOrder: Int,
        @Json(name = "is_deleted")
        override var isDeleted: Int,
        @Json(name = "responsible_uid")
        override var responsibleUid: Long?,
        @Json(name = "project_id")
        override var projectId: Long,
        @Json(name = "date_completed")
        override var dateCompleted: Date?,
        @Json(name = "collapsed")
        override var collapsed: Int,
        @Json(name = "date_string")
        override var dateString: String?
) : IItem