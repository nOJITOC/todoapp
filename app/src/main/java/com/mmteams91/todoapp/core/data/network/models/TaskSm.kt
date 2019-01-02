package com.mmteams91.todoapp.core.data.network.models

import com.mmteams91.todoapp.core.entities.ITask

data class TaskSm(
        override var completed: String,
        override var deleted: Boolean,
        override var due: String,
        override var etag: String,
        override var hidden: String,
        override var id: String,
        override var kind: String = "tasks#taskList",
        var links: List<LinkSm>,
        override var notes: String,
        override var parent: String,
        override var position: String,
        override var selfLink: String,
        override var status: String,
        override var title: String,
        override var updated: String
):ITask