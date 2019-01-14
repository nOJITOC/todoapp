package com.mmteams91.todoapp.features.synchronisation.data

import com.mmteams91.todoapp.common.entities.ICommand


data class InputSyncCommand(
        override var type: String
) : ICommand {
    companion object Types {
        const val SYNC_NEEDED = "sync_needed"
        const val AGENDA_UPDATED = "agenda_updated"
    }
}