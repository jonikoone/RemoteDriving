package com.jonikoone.remotedriving.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.data.Offset
import com.jonikoone.remotedriving.services.ServiceAPI
import com.jonikoone.remotedriving.ui.views.TouchScreen
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ControlDriveFragment : Fragment(), CoroutineScope, KodeinAware {
    override val kodein by kodein()
    override val coroutineContext = Dispatchers.Main

    companion object {
        private const val TAG_ADDRESS = "bundle tag address"
        fun newFragmet(address: String) = ControlDriveFragment().apply {
            val bundle = Bundle()
            bundle.putString(TAG_ADDRESS, address)
            this.arguments = bundle
        }
    }

    private val address: String by lazy {
        val res = arguments?.getString(TAG_ADDRESS, "") ?: ""
        Log.e("ControlerDrive:lazy", res)
        res
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_conrol_drive, container, false)

        val service: ServiceAPI by instance(arg = address)

        launch(Dispatchers.IO) {
            service.sendCommand("Hello").execute()
        }

        view.findViewById<TextView>(R.id.txtNameConnection)?.apply {

        }

        view.findViewById<TouchScreen>(R.id.cvTouchScreen)?.apply {
            this.onSendPosotion = {
                //send offset of position on server
                launch(Dispatchers.IO) {
                    service.sendPosition(it).execute()
                }
            }
        }

        return view
    }



}
