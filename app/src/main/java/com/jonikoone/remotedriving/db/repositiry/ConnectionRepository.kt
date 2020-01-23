package com.jonikoone.remotedriving.db.repositiry

import com.jonikoone.remotedriving.db.dao.ConnectionDAO
import com.jonikoone.remotedriving.db.entites.ConnectionEntity

class ConnectionRepository(private val connectionDAO: ConnectionDAO) {

    fun getConnections() = connectionDAO.getConnections()

    suspend fun addConnection(connection: ConnectionEntity) = connectionDAO.insertConnection(connection)
}