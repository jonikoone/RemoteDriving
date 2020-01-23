package com.jonikoone.remotedriving.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jonikoone.remotedriving.db.dao.ConnectionDAO
import com.jonikoone.remotedriving.db.entites.ConnectionEntity

@Database(entities = arrayOf(ConnectionEntity::class), version = 1, exportSchema = false)
abstract class ConnectionsDataBase : RoomDatabase() {
    companion object {
        var injectContext: Context? = null
        val instance: ConnectionsDataBase by lazy {
            Room
                //TODO !! - is bad(
                .databaseBuilder(injectContext!!, ConnectionsDataBase::class.java, "firs_db")
                .build()
        }
    }

    abstract fun connectionDao(): ConnectionDAO
}


