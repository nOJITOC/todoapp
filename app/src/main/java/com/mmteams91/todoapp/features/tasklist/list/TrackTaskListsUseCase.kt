package com.mmteams91.todoapp.features.tasklist.list

import com.mmteams91.todoapp.core.domain.usecases.base.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class TrackTaskListsUseCase @Inject constructor(
        private val taskListsRepository: ITaskListRepository,
        private val transformer: TaskListFromRecordTransformer = TaskListFromRecordTransformer()
) : FlowableUseCase<Unit, List<TaskListVm>>() {
    override fun execute(requestValue: Unit): Flowable<List<TaskListVm>> {
        return taskListsRepository.getTaskLists().map { list -> list.map { transformer.transform(it) } }
    }

}