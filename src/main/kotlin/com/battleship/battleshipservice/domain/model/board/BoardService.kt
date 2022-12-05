package com.battleship.battleshipservice.domain.model.board

import com.battleship.battleshipservice.domain.board.Board
import com.battleship.battleshipservice.domain.board.BoardVO
import com.battleship.battleshipservice.domain.board.Coordinate
import com.battleship.battleshipservice.domain.player.Player
import java.util.UUID

interface BoardService {
    fun create(playerUsername: String): BoardVO
    fun attackMove(
        boardAttackId: UUID,
        coordinateAttack: Coordinate,
        attackingPlayer: Player
    ): String
}