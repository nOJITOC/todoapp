package com.mmteams91.todoapp.features.user.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.common.presentation.view.BaseFragment
import com.mmteams91.todoapp.common.presentation.viewmodel.Event
import com.mmteams91.todoapp.features.user.auth.AuthViewModel.Companion.SHOW_ERROR
import kotlinx.android.synthetic.main.auth_fragment.*

class AuthFragment : BaseFragment<AuthViewModel>(AuthViewModel::class.java) {

    companion object {
        fun newInstance() = AuthFragment()
    }

    override fun obtainEvent(event: Event) {
        when (event.name) {
            SHOW_ERROR -> {
                error.text = getString(event.typedPayload<Int>()!!)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login.setOnClickListener { viewModel.tryAuth(email.text?.trimEnd().toString(), password.text.toString()) }
    }
}
