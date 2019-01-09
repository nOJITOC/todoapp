package com.mmteams91.todoapp.core.data.network.models

import com.mmteams91.todoapp.core.entities.IFeatures
import com.squareup.moshi.Json

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