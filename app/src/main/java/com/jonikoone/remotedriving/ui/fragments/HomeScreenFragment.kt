package com.jonikoone.remotedriving.ui.fragments

import android.content.Context
import android.content.Intent
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
import com.jonikoone.remotedriving.db.ConnectionsDataBase
import com.jonikoone.remotedriving.recyclers.adapters.ConnectionsViewAdapter
import com.jonikoone.remotedriving.ui.screens.CreateConnectionScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

class HomeScreenFragment : Fragment(), CoroutineScope, KodeinAware {
    override val coroutineContext = Dispatchers.Main
    override val kodein by kodein()

    private val cicerone by instance<Cicerone<Router>>()

    private val adapter = ConnectionsViewAdapter()

    private val database by instance<ConnectionsDataBase>()

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.rvConnections)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@HomeScreenFragment.adapter
        }

        view.findViewById<Button>(R.id.btnAddConnection)?.apply {
            setOnClickListener {
                cicerone.router.navigateTo(CreateConnectionScreen())
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        //update recyclerView adapter
        launch(Dispatchers.IO) {
            val connections = database.connectionDao().getConnections()
            Log.e("Home", connections.toString())
            withContext(Dispatchers.Main) {
                adapter.addConnections(connections)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        adapter.addConnections(listOf())
    }


}