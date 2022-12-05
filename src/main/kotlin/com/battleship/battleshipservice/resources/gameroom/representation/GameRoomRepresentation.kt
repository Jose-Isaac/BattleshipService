package com.battleship.battleshipservice.resources.gameroom.representation

import com.battleship.battleshipservice.domain.board.BoardVO
import com.battleship.battleshipservice.domain.player.ActivityType

@kotlinx.serialization.Serializable
data class GameRoomRepresentation(
    val activityType: ActivityType,
    val gameRoomId: String,
    val playerOne: String,
    val playerTwo: String,
    val boardOne: BoardVO,
    val boardTwo: BoardVO,
    val playerTurn: String,
    val turn: Int,
    val winner: String?
)