package com.jonikoone.remotedriving.db.dao

import androidx.room.*
import com.jonikoone.remotedriving.db.entites.ConnectionEntity

@Dao
interface ConnectionDAO {

    @Query(value = "Select * from connections_table order by id")
    suspend fun getConnections(): List<ConnectionEntity>

    @Query(value = "Select * from connections_table where id=:id")
    suspend fun getConnection(id: Int): ConnectionEntity?

    @Insert
    suspend fun insertConnection(connection: ConnectionEntity)

    @Delete
    suspend fun deleteConnection(connection: ConnectionEntity)

    @Update
    suspend fun updateConnection(connection: ConnectionEntity)
}