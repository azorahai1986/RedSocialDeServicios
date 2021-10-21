 package com.hernan.redsocialdeservicios.push_notification

import com.hernan.redsocialdeservicios.push_notification.Constantes.Companion.CONTENT_TYPE
import com.hernan.redsocialdeservicios.push_notification.Constantes.Companion.SERVER_KEY
import com.squareup.okhttp.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi{

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(@Body notification: PushNotification): retrofit2.Response<ResponseBody>
}