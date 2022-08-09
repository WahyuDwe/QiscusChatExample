package com.example.qiscuschatexample

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.qiscuschatexample.databinding.ActivityMainBinding
import com.qiscus.multichannel.QiscusMultichannelWidget
import com.qiscus.multichannel.QiscusMultichannelWidgetColor
import com.qiscus.multichannel.QiscusMultichannelWidgetConfig
import com.qiscus.multichannel.util.MultichannelNotificationListener
import com.qiscus.multichannel.util.QiscusChatRoomBuilder
import com.qiscus.sdk.chat.core.QiscusCore
import com.qiscus.sdk.chat.core.data.model.QChatRoom
import com.qiscus.sdk.chat.core.data.model.QMessage

class MainActivity : AppCompatActivity() {

    lateinit var qiscusMultichannelWidget: QiscusMultichannelWidget
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val qiscusCore = QiscusCore()
        val appId = "" // change with your AppId
        val localKey = "" // change with your localKey
        val config = QiscusMultichannelWidgetConfig()
//            .setEnableLog(false)
            .setEnableNotification(true)
            .setNotificationListener(object : MultichannelNotificationListener {
                override fun handleMultichannelListener(
                    context: Context?,
                    qiscusComment: QMessage?
                ) {
                    // set up your notification

                }
            })
        val color = QiscusMultichannelWidgetColor()
            .setStatusBarColor(R.color.purple_700)
            .setNavigationColor(R.color.purple_500)
            .setRightBubbleColor(R.color.purple_500)
            .setSendContainerColor(R.color.purple_500)


        qiscusMultichannelWidget = QiscusMultichannelWidget.setup(
            application,
            qiscusCore,
            appId,
            config,
            color,
            localKey
        )


        if (qiscusMultichannelWidget.hasSetupUser()) {
            FirebaseService().getCurrentToken()
        }

        qiscusMultichannelWidget.setUser(
            userId = "1",
            "Dummy User",
            avatar = "https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=740&t=st=1659946112~exp=1659946712~hmac=723d56fc2add5504db8395b4d084c0675c2c178cd89eb562faabc807e87dbb01"
        )

        isLoading(false)
        binding.btnStartChat.setOnClickListener {
            initChat()
        }
    }

    private fun initChat() {
        qiscusMultichannelWidget.initiateChat()
            .showLoadingWhenInitiate(false)
//            .setChannelId() // change with your channel id
            .setRoomTitle("Custom Title")
            .setAvatar(QiscusMultichannelWidgetConfig.Avatar.DISABLE)
            .setRoomSubtitle(
                QiscusMultichannelWidgetConfig.RoomSubtitle.EDITABLE,
                "Custom Subtitle"
            )
            .setShowSystemMessage(true)
            .setSessional(false)
            .startChat(this, object : QiscusChatRoomBuilder.InitiateCallback {
                override fun onSuccess(qChatRoom: QChatRoom) {
                    isLoading(false)
                    Log.i("InitialCallback", "onSuccess")
                    qiscusMultichannelWidget.openChatRoomById(
                        this@MainActivity,
                        qChatRoom.id,
                        true
                    ) { throwable ->
                        throwable.printStackTrace()
                    }
                }

                override fun onProgress() {
                    isLoading(true)
                    Log.i("InitialCallback", "onProgress")
                }

                override fun onError(throwable: Throwable) {
                    isLoading(false)
                    Log.i("InitialCallback", "onError: ${throwable.message}")
                }

            })

    }

    private fun isLoading(state: Boolean) {
        binding.progressBar.isVisible = state
    }
}