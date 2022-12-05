package com.battleship.battleshipservice.domain.player

import org.springframework.web.socket.WebSocketSession

data class PlayerSession(
    val sessionsId: MutableList<String>,
    val username: String
)
