package com.mmteams91.todoapp.features.agenda


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.common.presentation.view.BaseFragment
import com.mmteams91.todoapp.common.presentation.viewmodel.Event

class AgendaFragment : BaseFragment<AgendaViewModel>(AgendaViewModel::class.java) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun obtainEvent(event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance() = AgendaFragment()
    }
}
