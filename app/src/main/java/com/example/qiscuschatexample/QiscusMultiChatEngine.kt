package com.example.qiscuschatexample

import com.qiscus.sdk.chat.core.QiscusCore

class QiscusMultiChatEngine {
    private val qiscusCores: MutableList<QiscusCore> = ArrayList()

    init {
        qiscusCores.add(MULTICHANNEL_CORE, QiscusCore())
        qiscusCores.add(ANOTHERCHANNEL_CORE, QiscusCore())
    }

    fun get(type: Int): QiscusCore {
        return qiscusCores[type]
    }

    fun getAll(): MutableList<QiscusCore> {
        return qiscusCores
    }

    companion object {
        val MULTICHANNEL_CORE = 0
        val ANOTHERCHANNEL_CORE = 1
    }
}