package com.battleship.battleshipservice.domain.gameroom

import com.battleship.battleshipservice.domain.board.Board
import com.battleship.battleshipservice.domain.board.BoardVO
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

data class GameRoomVO(
    val id: UUID = UUID.randomUUID(),
    val boardPlayerOne: BoardVO,
    val boardPlayerTwo: BoardVO,
    val playerTurn: String,
    val turn: Int = 1,
    val winner: String? = null
)
