package com.battleship.battleshipservice.domain.model.gameroom

import com.battleship.battleshipservice.domain.board.Coordinate
import com.battleship.battleshipservice.domain.gameroom.GameRoom
import com.battleship.battleshipservice.domain.gameroom.GameRoomVO
import com.battleship.battleshipservice.domain.gameroom.PendingInvitation
import com.battleship.battleshipservice.resources.gameroom.representation.GameRoomRepresentation
import com.battleship.battleshipservice.resources.gameroom.representation.JoinRepresentation
import java.util.UUID

interface GameRoomService {
    fun createInvitation(): UUID
    fun removeInvitation(invitation: UUID)
    fun create(pendingInvitation: PendingInvitation, currentPlayer: String): GameRoomVO
    fun join(joinRepresentation: JoinRepresentation): GameRoomRepresentation
    fun attackMove(
        gameRoomId: UUID,
        boardAttackId: UUID,
        attackingPlayer: String,
        coordinate: Coordinate
    ): GameRoomRepresentation
}
