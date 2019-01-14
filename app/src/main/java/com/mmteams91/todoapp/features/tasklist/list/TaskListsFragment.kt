package com.mmteams91.todoapp.features.tasklist.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.common.presentation.view.BaseFragment
import com.mmteams91.todoapp.common.presentation.viewmodel.Event
import com.mmteams91.todoapp.common.presentation.ui.adapters.ListDelegationAdapterBuilder

class TaskListsFragment : BaseFragment<TaskListsViewModel>(TaskListsViewModel::class.java) {
    private val adapter by lazy {
        ListDelegationAdapterBuilder<TaskListVm>()
                .build()
    }

    override fun obtainEvent(event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.task_lists_fragment, container, false)
    }

    companion object {
        fun newInstance() = TaskListsFragment()
    }

}
