package com.hernan.redsocialdeservicios.push_notification

import com.hernan.redsocialdeservicios.push_notification.Constantes.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofitinstance {
    companion object{
        private val retrofit by lazy {

                 Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

        }
        val api by lazy {
            retrofit.create(NotificationApi::class.java)
        }
    }
}