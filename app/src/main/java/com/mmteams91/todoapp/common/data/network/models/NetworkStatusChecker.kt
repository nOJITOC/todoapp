package com.mmteams91.todoapp.common.data.network.models

import android.content.Context
import android.net.ConnectivityManager
import com.mmteams91.todoapp.features.synchronisation.SocketMessagesProvider
import io.reactivex.Single
import javax.inject.Inject

class NetworkStatusChecker @Inject constructor(
        context: Context,
        private val socketMessagesProvider: SocketMessagesProvider
) {
    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable() = socketMessagesProvider.isStarted() || connectivityManager.activeNetworkInfo?.isConnected == true

    fun isNetworkAvailableSinlge() = Single.just(isNetworkAvailable())
}