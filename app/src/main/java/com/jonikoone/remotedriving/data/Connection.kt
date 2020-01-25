package com.jonikoone.remotedriving.data

import com.jonikoone.remotedriving.db.entites.ConnectionEntity

@Deprecated(message = "Use ConnectionEntity", level = DeprecationLevel.ERROR)
data class Connection(
    val name: String,
    val address: String
)