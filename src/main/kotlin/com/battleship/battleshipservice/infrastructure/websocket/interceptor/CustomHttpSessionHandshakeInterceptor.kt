package com.battleship.battleshipservice.infrastructure.websocket.interceptor

import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor

class CustomHttpSessionHandshakeInterceptor : HttpSessionHandshakeInterceptor() {

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
//        val checkDefaultHandshake = super.beforeHandshake(request, response, wsHandler, attributes)
//        val isAuthenticated = (request.principal as UsernamePasswordAuthenticationToken).isAuthenticated
//        response.setStatusCode(HttpStatus.OK)
//        return checkDefaultHandshake && isAuthenticated
        return super.beforeHandshake(request, response, wsHandler, attributes)
    }
}