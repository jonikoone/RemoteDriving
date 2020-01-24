package com.jonikoone.remotedriving.recyclers.adapters

import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.db.entites.ConnectionEntity
import com.jonikoone.remotedriving.recyclers.viewHolders.ConnectionViewHolder

class ConnectionsViewAdapter: RecyclerView.Adapter<ConnectionViewHolder>() {
    private val connections = mutableListOf<ConnectionEntity>()

    fun addConnection(newConnection: ConnectionEntity) {
        connections += newConnection
        notifyItemRangeInserted(connections.lastIndex, 1)
    }

    fun addConnections(connections: List<ConnectionEntity>) {
        this.connections += connections
        notifyItemRangeInserted(this.connections.size, connections.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectionViewHolder {
        val view =
            View.inflate(parent.context, R.layout.fragment_item_connection_view, parent) as CardView
        return ConnectionViewHolder(view)
    }

    override fun getItemCount() = connections.size

    override fun onBindViewHolder(holder: ConnectionViewHolder, position: Int) {
        holder.onBind(connections[position])
    }

}