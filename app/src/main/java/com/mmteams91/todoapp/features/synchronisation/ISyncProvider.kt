package com.mmteams91.todoapp.features.synchronisation

import com.mmteams91.todoapp.common.entities.ICommand
import io.reactivex.Flowable

interface ISyncProvider<T:ICommand> {

    fun syncFlow():Flowable<List<T>>
}