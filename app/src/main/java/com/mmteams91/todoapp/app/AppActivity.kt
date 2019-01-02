package com.mmteams91.todoapp.app

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.core.data.permissions.PermissionTracker
import com.mmteams91.todoapp.core.extensions.resolveColor
import com.mmteams91.todoapp.core.extensions.safeSubscribe
import com.mmteams91.todoapp.core.presentation.IDisposableBinder
import com.mmteams91.todoapp.core.presentation.view.DisposableBinder
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel
import com.mmteams91.todoapp.app.AppViewModel.Events.CHECK_PERMISSION
import com.mmteams91.todoapp.app.AppViewModel.Events.ERROR
import com.mmteams91.todoapp.app.AppViewModel.Events.HIDE_PROGRESS
import com.mmteams91.todoapp.app.AppViewModel.Events.SHOW_PROGRESS
import com.mmteams91.todoapp.core.presentation.viewmodel.Event
import com.mmteams91.todoapp.core.presentation.viewmodel.EventWithPayload
import com.mmteams91.todoapp.core.presentation.viewmodel.ViewModelFactory
import com.mmteams91.todoapp.features.auth.AuthFragment
import kotlinx.android.synthetic.main.app_activity.*
import javax.inject.Inject

class AppActivity : AppCompatActivity(), IAppView, LifecycleObserver {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var permissionTracker: PermissionTracker
    override var disposableBinder: IDisposableBinder = DisposableBinder.on(this)
    override lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        initViewModel()
        lifecycle.addObserver(this)
        setContentView(R.layout.app_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AuthFragment.newInstance())
                    .commitNow()
        }
    }

    private fun initComponent() {
        (application as App).appComponent.inject(this)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(AppViewModel::class.java, viewModelFactory)
        viewModel.onCreate()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun viewOnStart() {
        super.viewOnStart()
    }

    override fun obtainEvent(event: Event) {
        when (event.name) {
            SHOW_PROGRESS -> showProgress()
            HIDE_PROGRESS -> hideProgress()
            ERROR -> showError(event as? EventWithPayload ?: return)
            CHECK_PERMISSION -> {
                event as EventWithPayload
                val payload = event.getTypedPayload<Pair<String, (Boolean) -> Unit>>()
                val permission = payload.first
                val onGrant = payload.second
                permissionTracker.requestPermission(permission)
                        .safeSubscribe {
                            onGrant.invoke(it)
                        }.also { disposableBinder.bindToLifecycleUntil(Lifecycle.Event.ON_DESTROY, it) }
            }
        }
    }

    private fun showError(event: EventWithPayload) {
        val message = if (event.payload is Int) getString(event.payload) else event.payload.toString()
        val snackbar = Snackbar.make(container, message, Snackbar.LENGTH_LONG)
        val view = snackbar.view
        val context = view.context
        view.setBackgroundColor(context.resolveColor(R.attr.colorError))
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionTracker.checkPermission(requestCode, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun <T : BaseViewModel> obtainViewModel(viewModelClass: Class<T>, viewModelFactory: ViewModelFactory) =
            ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)


}
