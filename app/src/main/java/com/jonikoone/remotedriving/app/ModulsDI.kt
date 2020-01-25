package com.jonikoone.remotedriving.app

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jonikoone.remotedriving.services.ServiceAPI
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.multiton
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = Kodein.Module(name = "network module") {
    bind<Gson>() with singleton { GsonBuilder().setLenient().create() }

    bind<OkHttpClient>() with singleton { OkHttpClient.Builder().build() }

    bind<ServiceAPI>() with multiton { address: String ->
        Retrofit.Builder()
            .baseUrl("http://$address/")
            .client(instance())
            .addConverterFactory(GsonConverterFactory.create(instance()))
            .build()
            .create(ServiceAPI::class.java)
    }
}