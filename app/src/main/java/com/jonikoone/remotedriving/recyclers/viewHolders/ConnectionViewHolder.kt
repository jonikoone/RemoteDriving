package com.jonikoone.remotedriving.recyclers.viewHolders

import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.db.entites.ConnectionEntity
import com.jonikoone.remotedriving.ui.screens.ControlDriveScreen
import com.jonikoone.remotedriving.ui.screens.EditConnectionScreen
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

//TODO: change cardview to custom view may be
class ConnectionViewHolder(private val viewCard: CardView) : RecyclerView.ViewHolder(viewCard),
    KodeinAware {
    override val kodein by kodein(viewCard.context)
    private val cicerone by instance<Cicerone<Router>>()

    fun onBind(connection: ConnectionEntity) {
        if (connection.id != null) {
            viewCard.findViewById<TextView>(R.id.txtNameConnection)?.apply {
                text = connection.name
            }

            viewCard.findViewById<TextView>(R.id.txtAddressConnection)?.apply {
                text = resources.getString(
                    R.string.pattern_address_port,
                    connection.address,
                    connection.port
                )
            }

            viewCard.findViewById<Button>(R.id.btnConnect)?.apply {
                setOnClickListener {
                    cicerone.router.navigateTo(
                        ControlDriveScreen(
                            name = connection.name,
                            address = "${connection.address}:${connection.port}"
                        )
                    )
                }
            }

            viewCard.setOnClickListener {
                connection.id?.let { id ->
                    cicerone.router.navigateTo(EditConnectionScreen(id))
                }
            }

            /*viewCard.setOnLongClickListener { view ->
                connection.id?.let { id ->
                    Snackbar.make(view, connection.name, Snackbar.LENGTH_LONG)
                        .setAction("Delete") {  }
                        .show()
                }
                true
            }*/
        }
    }

}