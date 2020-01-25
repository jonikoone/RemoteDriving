package com.jonikoone.remotedriving.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jonikoone.remotedriving.R

class CreateConnectionScreenFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_connection_screen, container, false)

        //TODO: перенесу после изучения moxy или других библиотек для MV(P,I,VM..)
        val txtIpAddress = view.findViewById<TextView>(R.id.txtIpAddress)
        val txtIpPort = view.findViewById<TextView>(R.id.txtIpPort)
        val txtLog = view.findViewById<TextView>(R.id.txtLog)

        view.findViewById<Button>(R.id.btnTryNewConnection)?.apply {
            setOnClickListener {

            }
        }

        view.findViewById<Button>(R.id.btnSaveConnetion)?.apply {

        }

        return view
    }

}