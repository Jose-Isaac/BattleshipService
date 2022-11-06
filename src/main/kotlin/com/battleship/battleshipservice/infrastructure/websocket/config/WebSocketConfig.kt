package com.jisaacbc.battleshipservice.infrastructure.websocket.config

import com.jisaacbc.battleshipservice.infrastructure.websocket.handle.HandleGameRoom
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(HandleGameRoom(), "/game-room/*").setAllowedOriginPatterns("*")
    }
}
