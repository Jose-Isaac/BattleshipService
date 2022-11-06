package com.jisaacbc.battleshipservice.infrastructure.websocket.handle

import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class HandleGameRoom : TextWebSocketHandler() {
    @Throws(Exception::class)
    override fun afterConnectionEstablished(session: WebSocketSession) {
        println("received connection \n $session")
    }
}