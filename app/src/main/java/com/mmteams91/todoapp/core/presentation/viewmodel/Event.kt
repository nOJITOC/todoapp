package com.mmteams91.todoapp.core.presentation.viewmodel

open class Event(val name: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Event
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

class EventWithPayload(name: String, val payload: Any) : Event(name) {
    inline fun <reified T> getTypedPayload() = payload as T
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        other as EventWithPayload
        if (payload != other.payload) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + payload.hashCode()
        return result
    }

}
