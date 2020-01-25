package com.jonikoone.remotedriving.recyclers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.db.entites.ConnectionEntity
import com.jonikoone.remotedriving.recyclers.viewHolders.ConnectionViewHolder
import com.jonikoone.remotedriving.ui.screens.ControlDriveScreen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class ConnectionsViewAdapter : RecyclerView.Adapter<ConnectionViewHolder>(){

    private val connections = mutableListOf<ConnectionEntity>()

    fun addConnection(newConnection: ConnectionEntity) {
        connections += newConnection
        notifyItemRangeInserted(connections.lastIndex, 1)
    }

    fun addConnections(connections: List<ConnectionEntity>) {
        this.connections.clear()
        this.connections += connections
        notifyItemRangeInserted(this.connections.size, connections.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectionViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_connection_view, parent, false)
        view as CardView
        /*view.findViewById<Button>(R.id.btnSaveConnetion)?.apply {
            setOnClickListener {
                cicerone.router.navigateTo(ControlDriveScreen())
            }
        }*/
        return ConnectionViewHolder(view)
    }

    override fun getItemCount() = connections.size

    override fun onBindViewHolder(holder: ConnectionViewHolder, position: Int) {
        holder.onBind(connections[position])
    }

}