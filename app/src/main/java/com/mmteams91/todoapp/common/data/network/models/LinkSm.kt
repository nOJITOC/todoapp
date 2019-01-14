package com.mmteams91.todoapp.common.data.network.models

import com.mmteams91.todoapp.common.entities.ILink
import com.mmteams91.todoapp.common.extensions.EMPTY

data class LinkSm(
        override var description: String= String.EMPTY,
        override var link: String= String.EMPTY,
        override var type: String= String.EMPTY
):ILink