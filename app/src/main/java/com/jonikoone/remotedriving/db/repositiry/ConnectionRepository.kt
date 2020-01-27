package com.jonikoone.remotedriving.db.repositiry

import com.jonikoone.remotedriving.db.dao.ConnectionDAO
import com.jonikoone.remotedriving.db.entites.ConnectionEntity

class ConnectionRepository(private val connectionDAO: ConnectionDAO) {

    suspend fun getConnections() = connectionDAO.getConnections()

    suspend fun addConnection(connection: ConnectionEntity) = connectionDAO.insertConnection(connection)

    suspend fun deleteConnection(connection: ConnectionEntity) = connectionDAO.deleteConnection(connection)

    suspend fun updateConnection(connection: ConnectionEntity) = connectionDAO.updateConnection(connection)
}