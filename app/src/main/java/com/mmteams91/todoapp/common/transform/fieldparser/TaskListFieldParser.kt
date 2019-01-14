package com.mmteams91.todoapp.common.transform.fieldparser

import com.mmteams91.todoapp.common.entities.ITaskList

class TaskListFieldParser:FieldParser<ITaskList,ITaskList> {
    override fun parseFields(from: ITaskList, to: ITaskList) {
        to.kind = from.kind
        to.etag = from.etag
        to.id = from.id
        to.title = from.title
        to.updated = from.updated
        to.selfLink = from.selfLink
    }
}