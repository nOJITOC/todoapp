package com.mmteams91.todoapp.features.tasklist.list

import com.mmteams91.todoapp.core.data.transform.fieldparser.FieldParser
import com.mmteams91.todoapp.core.data.transform.transformer.SimpleTransformer

class TaskListFromRecordTransformer : SimpleTransformer<TaskListRecord, TaskListVm>({ TaskListVm() }, object : FieldParser<TaskListRecord, TaskListVm> {
    override fun parseFields(from: TaskListRecord, to: TaskListVm) {
    }
})