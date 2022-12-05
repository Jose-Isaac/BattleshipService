package com.battleship.battleshipservice.infrastructure.websocket.handle

import com.battleship.battleshipservice.domain.player.PlayerSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class HandleGameRoom : TextWebSocketHandler() {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val playersActive: MutableList<PlayerSession> = mutableListOf()

    @Throws(Exception::class)
    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info("Connection stabilised \n $session")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
        logger.info("HandleTextMessage by session: $session and message: $message")
    }
}