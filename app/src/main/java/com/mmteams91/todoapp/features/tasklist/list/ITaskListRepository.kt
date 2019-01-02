package com.mmteams91.todoapp.features.tasklist.list

import io.reactivex.Flowable

interface ITaskListRepository {

    fun getTaskLists():Flowable<List<TaskListRecord>>
}
