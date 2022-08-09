package com.example.qiscuschatexample

import android.util.Log
import com.example.qiscuschatexample.QiscusMultiChatEngine.Companion.MULTICHANNEL_CORE
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.qiscus.multichannel.QiscusMultichannelWidget
import com.qiscus.sdk.chat.core.QiscusCore

class FirebaseService : FirebaseMessagingService() {

    private val qiscusCore = QiscusCore()
    private val qiscusMultiChatEngine = QiscusMultiChatEngine()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        QiscusMultichannelWidget.instance.registerDeviceToken(
            qiscusMultiChatEngine.get(MULTICHANNEL_CORE), token
        )
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (QiscusMultichannelWidget.instance.isMultichannelMessage(
                message,
                qiscusMultiChatEngine.getAll()
            )
        ) {
            Log.d("Qiscus", "notification")
            return
        }
    }

    fun getCurrentToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener OnCompleteListener@{ task: Task<String> ->
                if (!task.isSuccessful) {
                    Log.e("Qiscus", "get current token faile: " + task.exception)
                    return@OnCompleteListener
                }
                // enable this when u have entered ur fcm server key to qiscus
//                if (task.isSuccessful && task.result != null) {
//                    val currentToken = task.result
//                    currentToken.let {
//                        QiscusMultichannelWidget.instance.registerDeviceToken(
//                            qiscusCore, it
//                        )
//                    }
//                }
            }
    }
}