package com.mmteams91.todoapp.core.data.network.models

import com.mmteams91.todoapp.core.entities.ILink
import com.mmteams91.todoapp.core.extensions.EMPTY

data class LinkSm(
        override var description: String= String.EMPTY,
        override var link: String= String.EMPTY,
        override var type: String= String.EMPTY
):ILink