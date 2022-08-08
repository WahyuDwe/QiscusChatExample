package com.example.qiscuschatexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.qiscus.multichannel.QiscusMultichannelWidget
import com.qiscus.multichannel.QiscusMultichannelWidgetConfig
import com.qiscus.multichannel.util.QiscusChatRoomBuilder
import com.qiscus.sdk.chat.core.QiscusCore
import com.qiscus.sdk.chat.core.data.model.QChatRoom

class MainActivity : AppCompatActivity() {

    lateinit var qiscusMultichannelWidget: QiscusMultichannelWidget

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val qiscusCore = QiscusCore()
        val appId: String = "gane-7jdfmmr9i2vwwbd1" // change with your AppId
        val localKey: String = "amikomone_channel" // change with your localKey
        qiscusMultichannelWidget = QiscusMultichannelWidget.setup(
            application,
            qiscusCore,
            appId,
//            color,
            localKey
        )
        qiscusMultichannelWidget.setUser(userId = "1", "Dummy User", avatar = "https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?w=740&t=st=1659946112~exp=1659946712~hmac=723d56fc2add5504db8395b4d084c0675c2c178cd89eb562faabc807e87dbb01")
        initChat(125948)
    }

    private fun initChat(channelId: Int) {
        qiscusMultichannelWidget.initiateChat()
            .showLoadingWhenInitiate(false)
            .setChannelId(channelId) // manual set channels id
            .setRoomTitle("Dummy Title")
            .setAvatar(QiscusMultichannelWidgetConfig.Avatar.DISABLE)
            .setRoomSubtitle(
                QiscusMultichannelWidgetConfig.RoomSubtitle.EDITABLE,
                "Dummy subtitle"
            )
            .setShowSystemMessage(true)
            .setSessional(false)
            .startChat(this, object : QiscusChatRoomBuilder.InitiateCallback {
                override fun onProgress() {
                    Log.i("InitiateCallback", "onProgress: ")
                }

                override fun onSuccess(qChatRoom: QChatRoom) {
                    Log.i("InitiateCallback", "onSuccess: ")
                    qiscusMultichannelWidget.openChatRoomById(
                        this@MainActivity,
                        qChatRoom.id,
                        true
                    ) { throwable ->
                        throwable.printStackTrace()
                    }
                }

                override fun onError(throwable: Throwable) {
                    Log.e("InitiateCallback", "onError: ${throwable.message}")

                }
            })
        }
    }