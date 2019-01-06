package com.mmteams91.todoapp.core.data.socket


data class SocketMessage(
        var type: String
){
    companion object Types{
        const val SYNC_NEEDED = "sync_needed"
        const val AGENDA_UPDATED = "agenda_updated"
    }
}