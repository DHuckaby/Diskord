package com.jessecorbett.diskord.internal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jessecorbett.diskord.DiscordLifecycleManager
import com.jessecorbett.diskord.WebSocketCloseCode
import com.jessecorbett.diskord.api.gateway.GatewayMessage
import com.jessecorbett.diskord.exception.DiscordCompatibilityException
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

private val jsonMapper = ObjectMapper().findAndRegisterModules().setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)!!

class DiscordWebSocketListener(private val acceptMessage: (GatewayMessage) -> Unit, private val lifecycleManager: DiscordLifecycleManager) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        if (response.code() == 101) return
        println(response)
        TODO("Handle other issues")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val gatewayMessage = jsonMapper.readValue<GatewayMessage>(text)
        acceptMessage(gatewayMessage)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        TODO("This should never be called yet, we'll come back to it when it's time to implement ETF")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        val closeCode = WebSocketCloseCode.values().find { it.code == code } ?: throw DiscordCompatibilityException("Unexpected close code")
        lifecycleManager.closing(code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        val closeCode = WebSocketCloseCode.values().find { it.code == code } ?: throw DiscordCompatibilityException("Unexpected close code")
        lifecycleManager.closed(code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        lifecycleManager.failed(t, response)
    }
}
