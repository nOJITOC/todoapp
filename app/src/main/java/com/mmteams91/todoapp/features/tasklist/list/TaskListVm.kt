package com.mmteams91.todoapp.features.tasklist.list

import android.os.Parcelable
import com.mmteams91.todoapp.core.extensions.EMPTY
import com.mmteams91.todoapp.core.ui.models.IBaseVm
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class TaskListVm(
        var id: String = String.EMPTY,
        var title: String = String.EMPTY,
        var updated: Date = Date(),
        var taskCount: Int = 0,
        var doneTaskCount: Int = 0
) : Parcelable, IBaseVm