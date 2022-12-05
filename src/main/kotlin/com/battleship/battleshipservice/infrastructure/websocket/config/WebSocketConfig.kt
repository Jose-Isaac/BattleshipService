package com.battleship.battleshipservice.infrastructure.websocket.config

import com.battleship.battleshipservice.infrastructure.websocket.handle.HandleGameRoom
import com.battleship.battleshipservice.infrastructure.websocket.interceptor.CustomHttpSessionHandshakeInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.*
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor

//@Configuration
//@EnableWebSocket
//class WebSocketConfig : WebSocketConfigurer {
//    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
//        registry.addHandler(HandleGameRoom(), "/gameroom")
//            .setAllowedOriginPatterns("*")
//            .addInterceptors(CustomHttpSessionHandshakeInterceptor())
//    }
//
//
//}

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/app")
        registry.enableSimpleBroker("/topic")
        registry.setUserDestinationPrefix("/player")
    }
}
