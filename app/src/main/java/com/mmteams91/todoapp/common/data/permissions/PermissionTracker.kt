package com.mmteams91.todoapp.common.data.permissions

import android.app.Activity
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import com.mmteams91.todoapp.common.extensions.applyBoolean
import com.mmteams91.todoapp.common.extensions.isPermissionGranted
import com.mmteams91.todoapp.common.utils.Logger
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by mmaruknin on 28.12.18.
 * mc21
 */
class PermissionTracker @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private var onPermissionResultPublisher: PublishSubject<Pair<Int, Boolean>> = PublishSubject.create<Pair<Int, Boolean>>()

    private var activity: Activity? = null
    private var requestPermissionCode: Int = 1
        get() = field++


    fun bind(activity: Activity) {
        onPermissionResultPublisher = PublishSubject.create<Pair<Int, Boolean>>()
        this.activity = activity
    }

    fun unbind() {
        onPermissionResultPublisher.onComplete()
        activity = null
    }

    fun requestPermission(permission: String): Single<Boolean> {
        return activity?.takeIf { !it.isFinishing }?.let { activity ->
            val reqCode = requestPermissionCode
            val isPermissionGranted = activity.isPermissionGranted(permission)
            if (isPermissionGranted || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Single.just(isPermissionGranted)
            } else {
                val shouldShowRationale = activity.shouldShowRequestPermissionRationale(permission)
                if (!shouldShowRationale && isPermissionRequested(permission)) {
                    Single.just(false)
                } else onPermissionResultPublisher
                        .filter { result -> result.first == reqCode }
                        .map { result -> result.second }
                        .doOnSubscribe {
                            putRequestedPermission(permission)
                            Logger.d("requestPermissions in $activity, ($reqCode,$permission)")
                            activity.requestPermissions(arrayOf(permission), reqCode)
                        }
                        .firstOrError()
            }
        } ?: Single.just(false)

    }

    fun checkPermission(requestCode: Int, grantResults: IntArray) {
        Logger.d("checkPermission in $activity, ($requestCode,${grantResults.joinToString(transform = { (it == PackageManager.PERMISSION_GRANTED).toString() })})")
        onPermissionResultPublisher.onNext(Pair(requestCode, grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }))
    }


    private fun isPermissionRequested(permission: String) = sharedPreferences.getBoolean(PERMISSION_PREFIX + permission, false)

    private fun putRequestedPermission(permission: String) = sharedPreferences.applyBoolean(PERMISSION_PREFIX + permission, true)

    companion object {
        private const val PERMISSION_PREFIX = "Permission: "
    }
}