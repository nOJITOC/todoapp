package com.mmteams91.todoapp.app

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.core.presentation.IDisposableBinder
import com.mmteams91.todoapp.core.presentation.view.DisposableBinder
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel.Events.EVENT_ERROR
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel.Events.EVENT_HIDE_PROGRESS
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel.Events.EVENT_SHOW_PROGRESS
import com.mmteams91.todoapp.core.presentation.viewmodel.Event
import com.mmteams91.todoapp.core.presentation.viewmodel.EventWithPayload
import com.mmteams91.todoapp.core.presentation.viewmodel.ViewModelFactory
import com.mmteams91.todoapp.features.auth.AuthFragment
import kotlinx.android.synthetic.main.app_activity.*
import javax.inject.Inject

class AppActivity : AppCompatActivity(), com.mmteams91.todoapp.app.IAppView {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override var disposableBinder: IDisposableBinder = DisposableBinder.on(this)
    override lateinit var viewModel: AppViewModel
    override val snackBarContainer: View? by lazy { container }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        viewModel = obtainViewModel(AppViewModel::class.java, viewModelFactory)
        viewModel.onCreate()
        setContentView(R.layout.app_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AuthFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onStart() {
        super<AppCompatActivity>.onStart()
        super<IAppView>.onStart()
    }

    override fun obtainEvent(event: Event) {
        when (event.name) {
            EVENT_SHOW_PROGRESS -> showProgress()
            EVENT_HIDE_PROGRESS -> hideProgress()
            EVENT_ERROR -> showError(event as? EventWithPayload ?: return)
        }
    }

    private fun showError(event: EventWithPayload) {
        val message = if (event.payload is Int) getString(event.payload) else event.payload.toString()
        val snackbar = Snackbar.make(snackBarContainer ?: return, message, Snackbar.LENGTH_LONG)
        val view = snackbar.view
        val context = view.context
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
        snackbar.setActionTextColor(ContextCompat.getColor(context, android.R.color.white))
        snackbar.setAction(android.R.string.ok) {
            if (snackbar.isShown) {
                snackbar.dismiss()
            }
        }
        snackbar.show()
    }

    override fun hideProgress() {
        progress.hide()
    }

    override fun showProgress() {
        progress.show()
    }

    private fun <T : BaseViewModel> obtainViewModel(viewModelClass: Class<T>, viewModelFactory: ViewModelFactory) =
            ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)


}
