package com.mmteams91.todoapp.features.tasklist.list

import com.mmteams91.todoapp.core.domain.usecases.base.FlowableUseCase
import com.mmteams91.todoapp.core.domain.usecases.base.run
import com.mmteams91.todoapp.core.extensions.safeSubscribe
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseFragmentViewModel
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

class TaskListsViewModel(
        private val trackTaskListsUseCase: FlowableUseCase<Unit, List<TaskListVm>>
) : BaseFragmentViewModel() {
    private val taskLists = BehaviorProcessor.create<List<TaskListVm>>()

    fun tasks(): Flowable<List<TaskListVm>> = taskLists


    override fun onCreate() {
        super.onCreate()
        trackTasks()
    }

    private fun trackTasks() {
        trackTaskListsUseCase.run()
                .doOnSubscribe { appViewModel.publishShowProgress() }
                .distinctUntilChanged()
                .safeSubscribe(onNext = ::onDataChanged)
                .also { addDisposable(it) }
    }

    fun onDataChanged(data: List<TaskListVm>) {
        if (!taskLists.hasValue()) appViewModel.publishHideProgress()
        taskLists.onNext(data)
    }

    companion object {
        const val SHOW_NO_TASKS = "Show no tasks"
    }

}
