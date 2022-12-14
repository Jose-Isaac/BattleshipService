package com.battleship.battleshipservice.domain.gameroom

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(
    name = "game_room"
)
data class GameRoom(
    @Id
    val id: UUID = UUID.randomUUID(),

    val boardPlayerOne: UUID,

    val boardPlayerTwo: UUID,

    val playerTurn: String,

    val turn: Int = 1,

    val winner: String? = null
)
