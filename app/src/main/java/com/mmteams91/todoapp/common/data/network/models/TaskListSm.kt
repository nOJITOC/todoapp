package com.mmteams91.todoapp.common.data.network.models

import com.mmteams91.todoapp.common.entities.ITaskList
import com.mmteams91.todoapp.common.extensions.EMPTY

data class TaskListSm(
        override var kind: String = "tasks#taskList",
        override var etag: String = String.EMPTY,
        override var id: String = String.EMPTY,
        override var title: String = String.EMPTY,
        override var updated: String = String.EMPTY,
        override var selfLink: String = String.EMPTY
):ITaskList