package com.jonikoone.remotedriving.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jonikoone.remotedriving.db.dao.ConnectionDAO
import com.jonikoone.remotedriving.db.entites.ConnectionEntity

@Database(entities = arrayOf(ConnectionEntity::class), version = 1, exportSchema = false)
abstract class ConnectionsDataBase : RoomDatabase() {
    abstract fun connectionDao(): ConnectionDAO
}


