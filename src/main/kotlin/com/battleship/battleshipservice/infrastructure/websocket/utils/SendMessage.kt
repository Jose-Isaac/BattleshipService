//package com.battleship.battleshipservice.infrastructure.websocket.utils
//
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//import org.springframework.stereotype.Service
//import org.springframework.web.socket.TextMessage
//import org.springframework.web.socket.WebSocketSession
//
//@Service
//class SendMessage {
//    fun to(session: WebSocketSession, message: ResponseMessage) {
//        return session.sendMessage(
//            TextMessage(
//                Json.encodeToString(message)
//            )
//        )
//    }
//
//    fun broadcast(users: HashMap<String, User>, message: ResponseMessage) {
//        users.forEach { UserMap ->
//            UserMap.value.sessions.forEach { session -> this.to(session, message)}
//        }
//    }
//
//    fun broadcastToOthers(
//        users: HashMap<String, User>,
//        currentSession: WebSocketSession,
//        username: String,
//        message: ResponseMessage
//    ) {
//        users.forEach { UserMap ->
//            if (UserMap.value.name == username) {
//                UserMap.value.sessions
//                    .filterNot { it == currentSession }
//                    .forEach { this.to(it, message) }
//            } else UserMap.value.sessions.forEach { session -> this.to(session, message)}
//        }
//    }
//}