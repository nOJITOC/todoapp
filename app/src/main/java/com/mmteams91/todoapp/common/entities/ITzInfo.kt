package com.mmteams91.todoapp.common.entities

interface ITzInfo {
    var hours: Int
    var timezone: String
    var isDst: Int
    var minutes: Int
    var gmtString: String
}