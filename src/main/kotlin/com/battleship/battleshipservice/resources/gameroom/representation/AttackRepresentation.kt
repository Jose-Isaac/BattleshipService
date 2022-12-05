package com.battleship.battleshipservice.resources.gameroom.representation

import com.battleship.battleshipservice.domain.board.Coordinate

data class AttackRepresentation(
    val gameRoomId: String,
    val boardAttackId: String,
    val attackingPlayer: String,
    val coordinate: Coordinate
)
