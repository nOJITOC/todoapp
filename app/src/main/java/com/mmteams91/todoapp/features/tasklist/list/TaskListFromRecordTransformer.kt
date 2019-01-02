package com.mmteams91.todoapp.features.tasklist.list

import com.mmteams91.todoapp.core.data.transform.fieldparser.TaskListFieldParser
import com.mmteams91.todoapp.core.data.transform.transformer.SimpleTransformer

class TaskListFromRecordTransformer : SimpleTransformer<TaskListRecord, TaskListVm>({ TaskListVm() }, TaskListFieldParser())