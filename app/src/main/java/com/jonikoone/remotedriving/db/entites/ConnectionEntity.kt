package com.jonikoone.remotedriving.db.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "connections_table")
data class ConnectionEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @NotNull
    @ColumnInfo(name = "name_connection")
    val name: String,
    @ColumnInfo(name = "address_connection")
    val address: String
)