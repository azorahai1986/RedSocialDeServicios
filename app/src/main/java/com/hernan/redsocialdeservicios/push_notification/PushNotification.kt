package com.hernan.redsocialdeservicios.push_notification

import android.content.ContentValues.TAG
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hernan.redsocialdeservicios.R
import kotlinx.coroutines.withContext

data class PushNotification(val data:NotificationData, val to:String)