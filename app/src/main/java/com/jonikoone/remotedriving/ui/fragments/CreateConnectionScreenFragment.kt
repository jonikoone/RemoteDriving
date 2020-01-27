package com.jonikoone.remotedriving.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.db.ConnectionsDataBase
import com.jonikoone.remotedriving.db.entites.ConnectionEntity
import kotlinx.android.synthetic.main.fragment_create_connection_screen.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class CreateConnectionScreenFragment : Fragment(), CoroutineScope, KodeinAware {
    override val kodein by kodein()
    override val coroutineContext = Dispatchers.Main

    private val database by instance<ConnectionsDataBase>()
    private val cicerone by instance<Cicerone<Router>>()

    companion object {
        private const val TAG_ID_CONNECTION = "Id connection"

        fun newFragment(idConnection: Int? = null) = CreateConnectionScreenFragment().apply {
            idConnection?.let {
                val arguments = Bundle()
                arguments.putInt(TAG_ID_CONNECTION, idConnection)
                this.arguments = arguments
            }
        }
    }

    private val idConnection: Int? by lazy { arguments?.getInt(TAG_ID_CONNECTION, -1) }
    private var connectionEntity = ConnectionEntity()

    private lateinit var prepareConnectionEntity: () -> ConnectionEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_connection_screen, container, false)

        //TODO: перенесу после изучения moxy или других библиотек для MV(P,I,VM..)
        val txtIpAddress = view.findViewById<TextView>(R.id.txtIpAddress)
        val txtIpPort = view.findViewById<TextView>(R.id.txtIpPort)
        val txtNameConnection = view.findViewById<TextView>(R.id.txtNameConnection)

        val textChange: (CharSequence?, Int, Int, Int) -> Unit = { text, start, count, after ->
            text?.apply {
                btnSaveConnection.isEnabled = !isEmpty()
            }
        }

        txtNameConnection.addTextChangedListener (onTextChanged = textChange)
        txtIpAddress.addTextChangedListener (onTextChanged = textChange)
        txtIpPort.addTextChangedListener (onTextChanged = textChange)

        val btnDeleteConnection = view.findViewById<Button>(R.id.btnDeleteConnection).apply {
            setOnClickListener {
                launch(Dispatchers.IO) {
                    database.connectionDao().deleteConnection(connectionEntity)
                    withContext(Dispatchers.Main) {
                        //                        Snackbar.make(this@apply, "Connection deleted", Snackbar.LENGTH_SHORT).show()
                        cicerone.router.exit()
                    }
                }
            }
        }

        prepareConnectionEntity = {
            connectionEntity.copy(
                name = txtNameConnection.text.toString(),
                address = txtIpAddress.text.toString(),
                port = txtIpPort.text.toString().toInt()
            )
        }

        //let load connection from db
        idConnection?.let { id ->
            launch {
                val connEntity =
                    async(Dispatchers.IO) { database.connectionDao().getConnection(id) }
                connEntity.await()?.let {
                    txtNameConnection.text = it.name
                    txtIpAddress.text = it.address
                    txtIpPort.text = it.port.toString()
                    connectionEntity = it

                    withContext(Dispatchers.Main) {
                        btnDeleteConnection.visibility = View.VISIBLE
                    }
                }
            }
        }

        view.findViewById<Button>(R.id.btnSaveConnection)?.apply {
            setOnClickListener {
                //                Snackbar.make(this, "Connection saved", Snackbar.LENGTH_SHORT).show()
                saveConnection(prepareConnectionEntity())
                cicerone.router.exit()
            }
        }

        return view
    }

    override fun onPause() {
        //save connection before exit to this screen
        //saveConnection(prepareConnectionEntity())
        super.onPause()
    }

    private fun saveConnection(connectionEntity: ConnectionEntity) {
        launch(Dispatchers.IO) {
            if (connectionEntity.id == null) {
                database.connectionDao().insertConnection(connectionEntity)
            } else {
                database.connectionDao().updateConnection(connectionEntity)
            }
            withContext(Dispatchers.Main) {
                //TODO: test
                /*Snackbar.make(
                    this@CreateConnectionScreenFragment.requireView(),
                    "Connection saved",
                    Snackbar.LENGTH_SHORT
                ).show()*/
            }

        }
    }

}