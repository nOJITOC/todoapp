package com.mmteams91.todoapp.features.project


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.core.presentation.view.BaseFragment
import com.mmteams91.todoapp.core.presentation.viewmodel.Event

class ProjectsFragment : BaseFragment<ProjectsViewModel>(ProjectsViewModel::class.java) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun obtainEvent(event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance() = ProjectsFragment()
    }
}
