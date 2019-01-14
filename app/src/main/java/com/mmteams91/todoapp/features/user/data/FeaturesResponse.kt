package com.mmteams91.todoapp.features.user.data

import com.mmteams91.todoapp.common.entities.IFeatures
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class FeaturesResponse(
        @Json(name = "beta")
        override var beta: Int,
        @Json(name = "dateist_inline_disabled")
        override var dateistInlineDisabled: Boolean,
        @Json(name = "dateist_lang")
        override var dateistLang: String?,
        @Json(name = "has_push_reminders")
        override var hasPushReminders: Boolean,
        @Json(name = "karma_disabled")
        override var karmaDisabled: Boolean,
        @Json(name = "karma_vacation")
        override var karmaVacation: Boolean,
        @Json(name = "restriction")
        override var restriction: Int
):IFeatures