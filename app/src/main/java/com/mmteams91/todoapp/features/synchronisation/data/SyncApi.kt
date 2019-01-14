package com.mmteams91.todoapp.features.synchronisation.data

import com.mmteams91.todoapp.features.synchronisation.data.models.SyncResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val SYNC = "sync"

interface SyncApi {

    @GET(SYNC)
    fun sync(@Query("sync_token") syncToken: String, @Query("resource_types") resourceTypes: Set<String>): Single<SyncResponse>
}
