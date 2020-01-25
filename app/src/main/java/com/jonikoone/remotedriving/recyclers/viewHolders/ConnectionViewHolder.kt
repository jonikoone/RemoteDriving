package com.jonikoone.remotedriving.recyclers.viewHolders

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.db.entites.ConnectionEntity
import com.jonikoone.remotedriving.ui.screens.ControlDriveScreen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

//TODO: change cardview to custom view may be
class ConnectionViewHolder(private val viewCard: CardView) : RecyclerView.ViewHolder(viewCard), KodeinAware {
    override val kodein by kodein(viewCard.context)
    private val cicerone by instance<Cicerone<Router>>()

    fun onBind(connection: ConnectionEntity) {
        viewCard.findViewById<TextView>(R.id.txtNameConnection)?.apply {
            text = connection.name
        }

        viewCard.findViewById<TextView>(R.id.txtAddressConnection)?.apply {
            text = connection.address
        }

        viewCard.findViewById<Button>(R.id.btnConnect)?.apply {
            setOnClickListener {
                cicerone.router.navigateTo(ControlDriveScreen(connection.address))
            }
        }
    }

}