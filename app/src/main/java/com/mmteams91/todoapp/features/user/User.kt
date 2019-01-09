package com.mmteams91.todoapp.features.user

import com.mmteams91.todoapp.core.entities.IFeatures
import com.mmteams91.todoapp.core.entities.ITzInfo
import com.mmteams91.todoapp.core.entities.IUser

data class User(
        override var startPage: String,
        override var autoReminder: Int,
        override var completedToday: Int,
        override var isPremium: Boolean,
        override var sortOrder: Int,
        override var fullName: String,
        override var apiToken: String,
        override var id: Long,
        override var shareLimit: Int,
        override var daysOff: List<Int>,
        override var magicNumReached: Boolean,
        override var nextWeek: Int,
        override var completedCount: Int,
        override var dailyGoal: Int,
        override var theme: Int,
        override var email: String,
        override var startDay: Int,
        override var weeklyGoal: Int,
        override var dateFormat: Int,
        override var websocketUrl: String,
        override var inboxProject: Long,
        override var timeFormat: Int,
        override var imageId: String?,
        override var karmaTrend: String,
        override var businessAccountId: Long?,
        override var mobileNumber: String?,
        override var mobileHost: String?,
        override var premiumUntil: String?,
        override var dateistLang: String?,
        override var joinDate: String,
        override var karma: Int,
        override var isBizAdmin: Boolean,
        override var defaultReminder: String,
        override var dateistInlineDisabled: Boolean,
        override var token: String,
        var features: Features,
        var tzInfo: TzInfo
) : IUser

data class Features(
        override var karmaDisabled: Boolean,
        override var restriction: Int,
        override var karmaVacation: Boolean,
        override var dateistLang: String?,
        override var beta: Int,
        override var hasPushReminders: Boolean,
        override var dateistInlineDisabled: Boolean
) : IFeatures

data class TzInfo(
        override var hours: Int,
        override var timezone: String,
        override var isDst: Int,
        override var minutes: Int,
        override var gmtString: String
) : ITzInfo