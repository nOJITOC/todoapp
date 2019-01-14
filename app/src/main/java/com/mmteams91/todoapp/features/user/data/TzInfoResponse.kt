package com.mmteams91.todoapp.features.user.data

import com.mmteams91.todoapp.common.entities.ITzInfo
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class TzInfoResponse(
        @Json(name = "gmt_string")
        override var gmtString: String,
        @Json(name = "hours")
        override var hours: Int,
        @Json(name = "is_dst")
        override var isDst: Int,
        @Json(name = "minutes")
        override var minutes: Int,
        @Json(name = "timezone")
        override var timezone: String
):ITzInfo