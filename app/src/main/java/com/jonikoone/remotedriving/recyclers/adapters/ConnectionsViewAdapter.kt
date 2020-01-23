package com.jonikoone.remotedriving.recyclers.adapters

import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.data.Connection
import com.jonikoone.remotedriving.recyclers.viewHolders.ConnectionViewHolder

class ConnectionsViewAdapter: RecyclerView.Adapter<ConnectionViewHolder>() {
    private val connections = mutableListOf<Connection>()

    fun addConnection(newConnection: Connection) {
        connections += newConnection
        notifyItemRangeInserted(connections.lastIndex, 1)
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