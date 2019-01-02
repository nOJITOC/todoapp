package com.mmteams91.todoapp.features.tasklist.list

import android.os.Parcelable
import com.mmteams91.todoapp.core.entities.ITaskList
import com.mmteams91.todoapp.core.extensions.EMPTY
import kotlinx.android.parcel.Parcelize

@Parcelize
open class TaskListRecord (
        override var kind: String = "tasks#taskList",
        override var etag: String = String.EMPTY,
        override var id: String = String.EMPTY,
        override var title: String = String.EMPTY,
        override var updated: String = String.EMPTY,
        override var selfLink: String = String.EMPTY
) : ITaskList, Parcelable