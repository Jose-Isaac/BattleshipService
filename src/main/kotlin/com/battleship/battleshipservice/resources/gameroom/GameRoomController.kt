package com.battleship.battleshipservice.resources.gameroom

import com.battleship.battleshipservice.domain.board.Board
import com.battleship.battleshipservice.domain.board.BoardVO
import com.battleship.battleshipservice.domain.board.Coordinate
import com.battleship.battleshipservice.domain.model.gameroom.GameRoomService
import com.battleship.battleshipservice.infrastructure.repositories.board.BoardRepository
import com.battleship.battleshipservice.resources.gameroom.representation.AttackRepresentation
import com.battleship.battleshipservice.resources.gameroom.representation.JoinRepresentation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(
    path = ["/gameroom"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class GameRoomController(
    private val gameRoomService: GameRoomService,
    private val boardRepository: BoardRepository,
    private val simpMessagingTemplate: SimpMessagingTemplate
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/create/invite")
    fun newGameRoom(): UUID {
        return gameRoomService.createInvitation()
        // TODO rename endpoint path
    }

    @MessageMapping("/join")
    fun join(
        @Payload join: JoinRepresentation
    ) {
        logger.info("Join payload: $join")
        val response = gameRoomService.join(join)
//        var board = Board(
//            id = UUID.randomUUID(),
//            plays = Json.encodeToString(mutableListOf(mutableListOf(Coordinate(0, 0, null, false, gotHit = false, wasHitBy = null)))),
//            player = "jose"
//        )

//        board = boardRepository.save(board)
        simpMessagingTemplate.convertAndSend(
            "/topic/gameroom/${join.inviteId}/response",
//            Json.encodeToString(BoardVO(player = board.player, plays = Json.decodeFromString(board.plays)))
            Json.encodeToString(response)
        )

        logger.info("Join response: $response")
    }

    @MessageMapping("/attack")
    fun attack(
        @Payload attackPayload: AttackRepresentation
    ) {
        logger.info("Attack payload: $attackPayload")

        val response = gameRoomService.attackMove(
            UUID.fromString(attackPayload.gameRoomId),
            UUID.fromString(attackPayload.boardAttackId),
            attackPayload.attackingPlayer,
            attackPayload.coordinate
        )

        simpMessagingTemplate.convertAndSend(
            "/topic/gameroom/${attackPayload.gameRoomId}/response",
//            Json.encodeToString(BoardVO(player = board.player, plays = Json.decodeFromString(board.plays)))
            Json.encodeToString(response)
        )

        logger.info("attack response: $response")
    }
}
