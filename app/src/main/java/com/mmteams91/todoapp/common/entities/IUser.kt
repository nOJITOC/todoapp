package com.mmteams91.todoapp.common.entities

interface IUser:IBaseEntity {
    var startPage: String
//    var features: IFeatures
    var autoReminder: Int
    var completedToday: Int
    var isPremium: Boolean
    var sortOrder: Int
    var fullName: String
    var apiToken: String
    var shareLimit: Int
    var daysOff: List<Int>
    var magicNumReached: Boolean
    var nextWeek: Int
    var completedCount: Int
    var dailyGoal: Int
    var theme: Int
//    var tzInfo: ITzInfo
    var email: String
    var startDay: Int
    var weeklyGoal: Int
    var dateFormat: Int
    var websocketUrl: String
    var inboxProject: Long
    var timeFormat: Int
    var imageId: String?
    var karmaTrend: String
    var businessAccountId: Long?
    var mobileNumber: String?
    var mobileHost: String?
    var premiumUntil: String?
    var dateistLang: String?
    var joinDate: String
    var karma: Int
    var isBizAdmin: Boolean
    var defaultReminder: String
    var dateistInlineDisabled: Boolean
    var token: String
}