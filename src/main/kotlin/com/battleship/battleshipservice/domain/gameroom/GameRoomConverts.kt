package com.battleship.battleshipservice.domain.gameroom

import com.battleship.battleshipservice.domain.board.BoardVO
import com.battleship.battleshipservice.domain.player.ActivityType
import com.battleship.battleshipservice.resources.gameroom.representation.GameRoomRepresentation

fun GameRoomVO.toRepresentation(activityType: ActivityType): GameRoomRepresentation {
    return GameRoomRepresentation(
        activityType = activityType,
        gameRoomId = this.id.toString(),
        playerOne = this.boardPlayerOne.player,
        boardOne = this.boardPlayerOne,
        playerTwo = this.boardPlayerTwo.player,
        boardTwo = this.boardPlayerTwo,
        playerTurn = this.playerTurn,
        turn = this.turn,
        winner = this.winner
    )
}

fun GameRoom.toVO(boardPlayerOne: BoardVO, boardPlayerTwo: BoardVO): GameRoomVO {
    return GameRoomVO(
        id = this.id,
        boardPlayerOne = boardPlayerOne,
        boardPlayerTwo = boardPlayerTwo,
        playerTurn = this.playerTurn,
        turn = this.turn,
        winner = this.winner
    )
}

