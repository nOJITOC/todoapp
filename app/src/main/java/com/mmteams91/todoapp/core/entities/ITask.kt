package com.mmteams91.todoapp.core.entities

interface ITask {
    var completed: String
    var deleted: Boolean
    var due: String
    var etag: String
    var hidden: String
    var id: String
    var kind: String
    var notes: String
    var parent: String
    var position: String
    var selfLink: String
    var status: String
    var title: String
    var updated: String
}