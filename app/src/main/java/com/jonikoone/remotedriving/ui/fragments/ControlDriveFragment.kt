package com.jonikoone.remotedriving.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.services.ServiceAPI
import com.jonikoone.remotedriving.ui.views.TouchScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import java.lang.Exception
import java.net.SocketTimeoutException

class ControlDriveFragment : Fragment(), CoroutineScope, KodeinAware {
    override val kodein by kodein()
    override val coroutineContext = Dispatchers.Main

    private val cicerone by instance<Cicerone<Router>>()

    companion object {
        private const val TAG_NAME = "bundle tag name connection"
        private const val TAG_ADDRESS = "bundle tag address"
        fun newFragmet(name: String, address: String) = ControlDriveFragment().apply {
            val bundle = Bundle()
            bundle.putString(TAG_NAME, name)
            bundle.putString(TAG_ADDRESS, address)
            this.arguments = bundle
        }

        private val TAG = this::class.java.canonicalName
    }

    private val nameConnection: String by lazy {
        arguments?.getString(TAG_NAME, "") ?: ""
    }

    private val address: String by lazy {
        arguments?.getString(TAG_ADDRESS, "") ?: ""
    }

    private val okHttpClient by instance<OkHttpClient>()
    private val gson by instance<Gson>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_conrol_drive, container, false)

        val txtNameConnection = view.findViewById<TextView>(R.id.txtNameConnection)

        /*if (address.isEmpty()
            || !address.contains(':')
            || address.count { ".".contains(it) } == 4) {

        }*/

        val service = Retrofit.Builder()
            .baseUrl("http://$address/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ServiceAPI::class.java)

        launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    txtNameConnection?.text = nameConnection + " trying..."
                }
                var body = service.sendCommand("Hello").execute().body()
                withContext(Dispatchers.Main) {
                    txtNameConnection?.text = nameConnection + " connect"
                }
                initControler(view, service)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    txtNameConnection?.apply {
                        setTextColor(resources.getColor(R.color.colorAccent))
                        text = resources.getString(R.string.connection_not_available)
                        Toast.makeText(context, "Timeout trying connection is out", Toast.LENGTH_LONG).show()
                        cicerone.router.exit()
                    }
                }
                Log.e(TAG, e.message, e)
            }
        }

        return view
    }

    fun initControler(view: View, service: ServiceAPI) {
        view.findViewById<TextView>(R.id.txtNameConnection)?.apply {

        }

        view.findViewById<TouchScreen>(R.id.cvTouchScreen)?.apply {
            sendPositionMouse = {
                //send offset of position on server
                launch(Dispatchers.IO) {
                    service.sendPosition(it).execute()
                }
            }

            sendPressMouseLeftButton = {
                launch(Dispatchers.IO) {
                    service.sendPresskMouseLeftButton().execute()
                }
            }

            sendReleaseMouseLeftButton = {
                launch(Dispatchers.IO) {
                    service.sendReleaseMouseLeftButton().execute()
                }
            }

            sendClickMouseLeftButton = {
                launch(Dispatchers.IO) {
                    service.sendClickMouseLeftButton().execute()
                }
            }

            sendScrollWheel = {
                launch(Dispatchers.IO) {
                    service.sendScrollWheel(it).execute()
                }
            }
        }
    }
}
