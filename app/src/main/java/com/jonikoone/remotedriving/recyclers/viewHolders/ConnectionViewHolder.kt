package com.jonikoone.remotedriving.recyclers.viewHolders

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.data.Connection

//TODO: change cardview to custom view may be
class ConnectionViewHolder(private val viewCard: CardView) : RecyclerView.ViewHolder(viewCard) {
    fun onBind(connection: Connection) {
        viewCard.findViewById<TextView>(R.id.txtNameConnection)?.apply {
            text = connection.name
        }

        viewCard.findViewById<TextView>(R.id.txtAddressConnection)?.apply {
            text = connection.address
        }

        viewCard.findViewById<Button>(R.id.btnConnect)?.apply {
            setOnClickListener {
                Toast.makeText(viewCard.context, "test connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

}