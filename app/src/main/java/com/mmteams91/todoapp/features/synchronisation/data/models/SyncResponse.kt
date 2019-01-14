package com.mmteams91.todoapp.features.synchronisation.data.models

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class SyncResponse(
        @Json(name = "sync_token")
        var syncToken: String,
        @Json(name = "full_sync")
        var fullSync: Boolean,
        var items: List<ItemSm>?,
        var projects: List<ProjectSm>?
)