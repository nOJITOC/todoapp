package com.mmteams91.todoapp.features.tasklist.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import com.mmteams91.todoapp.common.presentation.ui.models.IBaseVm
import kotlinx.android.extensions.LayoutContainer

class TaskListDelegate : AbsListItemAdapterDelegate<TaskListVm, IBaseVm,TaskListDelegate.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isForViewType(item: IBaseVm, items: MutableList<IBaseVm>, position: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(item: TaskListVm, viewHolder: ViewHolder, payloads: MutableList<Any>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

    }
}