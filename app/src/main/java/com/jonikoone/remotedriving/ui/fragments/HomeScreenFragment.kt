package com.jonikoone.remotedriving.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.recyclers.adapters.ConnectionsViewAdapter

class HomeScreenFragment : Fragment() {
    companion object {
        fun newFreament() = HomeScreenFragment()
    }

    val adapter: ConnectionsViewAdapter by lazy {
        ConnectionsViewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("HomeScreen", "onCreateView")
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        view.findViewById<RecyclerView>(R.id.rvConnections)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@HomeScreenFragment.adapter
        }

        view.findViewById<Button>(R.id.btnAddConnection)?.apply {
            setOnClickListener {
                childFragmentManager.beginTransaction()
                    .add(CreateConnectionScreenFragment.newFragment(), "create connection")
                    .commit()

            }
        }

        return view
    }



}

