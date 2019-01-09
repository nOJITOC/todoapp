package com.mmteams91.todoapp.core.data.network.models

import com.mmteams91.todoapp.core.entities.IUser
import com.squareup.moshi.Json

data class UserResponse(
        @Json(name = "id")
        override var id: Long,
        @Json(name = "token")
        override var token: String,
        @Json(name = "email")
        override var email: String,
        @Json(name = "full_name")
        override var fullName: String,
        @Json(name = "inbox_project")
        override var inboxProject: Long,
        @Json(name = "tz_info")
        var tzInfo: TzInfoResponse,
        @Json(name = "start_page")
        override var startPage: String,
        @Json(name = "start_day")
        override var startDay: Int,
        @Json(name = "next_week")
        override var nextWeek: Int,
        @Json(name = "date_format")
        override var dateFormat: Int,
        @Json(name = "time_format")
        override var timeFormat: Int,
        @Json(name = "sort_order")
        override var sortOrder: Int,
        @Json(name = "default_reminder")
        override var defaultReminder: String,
        @Json(name = "auto_reminder")
        override var autoReminder: Int,
        @Json(name = "mobile_host")
        override var mobileHost: String?,
        @Json(name = "mobile_number")
        override var mobileNumber: String?,
        @Json(name = "completed_count")
        override var completedCount: Int,
        @Json(name = "completed_today")
        override var completedToday: Int,
        @Json(name = "karma")
        override var karma: Int,
        @Json(name = "karma_trend")
        override var karmaTrend: String,
        @Json(name = "is_premium")
        override var isPremium: Boolean,
        @Json(name = "premium_until")
        override var premiumUntil: String?,
        @Json(name = "is_biz_admin")
        override var isBizAdmin: Boolean,
        @Json(name = "business_account_id")
        override var businessAccountId: Long?,
        @Json(name = "image_id")
        override var imageId: String?,
        @Json(name = "theme")
        override var theme: Int,
        @Json(name = "features")
        var features: FeaturesResponse,
        @Json(name = "join_date")
        override var joinDate: String,
        @Json(name = "api_token")
        override var apiToken: String,
        @Json(name = "daily_goal")
        override var dailyGoal: Int,
        @Json(name = "dateist_inline_disabled")
        override var dateistInlineDisabled: Boolean,
        @Json(name = "dateist_lang")
        override var dateistLang: String?,
        @Json(name = "days_off")
        override var daysOff: List<Int>,
        @Json(name = "magic_num_reached")
        override var magicNumReached: Boolean,
        @Json(name = "share_limit")
        override var shareLimit: Int,
        @Json(name = "websocket_url")
        override var websocketUrl: String,
        @Json(name = "weekly_goal")
        override var weeklyGoal: Int
):IUser