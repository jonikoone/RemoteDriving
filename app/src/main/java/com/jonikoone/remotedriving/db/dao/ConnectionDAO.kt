package com.jonikoone.remotedriving.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jonikoone.remotedriving.db.entites.ConnectionEntity

@Dao
interface ConnectionDAO {

    @Query(value = "Select * from connections_table order by id")
    fun getConnections(): List<ConnectionEntity>

    @Insert
    suspend fun insertConnection(connection: ConnectionEntity)

    //TODO: make delete by id
    /*@Query(value = "Delete from connections_table where name_connection=")
    suspend fun deleteConnection(connection: Connection)*/
}