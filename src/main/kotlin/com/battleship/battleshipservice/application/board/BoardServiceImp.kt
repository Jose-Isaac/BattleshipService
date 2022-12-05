package com.battleship.battleshipservice.application.board

import com.battleship.battleshipservice.domain.board.*
import com.battleship.battleshipservice.domain.model.board.BoardService
import com.battleship.battleshipservice.domain.player.Player
import com.battleship.battleshipservice.infrastructure.repositories.board.BoardRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class BoardServiceImp(
    private val boardRepository: BoardRepository
) : BoardService {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun create(playerUsername: String): BoardVO {
        logger.info("START - creating a new board")
        val initialPlays = populateInitialPlays()
        initialPlays[5] = mockInitialPosition(playerUsername)

        val board = boardRepository.save(
            Board(
                plays = Json.encodeToString(initialPlays),
                player = playerUsername
            )
        )

        logger.info("END - created a new board with initial plays: ${board.plays}")

        return board.toVO()
    }

    override fun attackMove(
        boardAttackId: UUID,
        coordinateAttack: Coordinate,
        attackingPlayer: Player
    ): String {
        val boardOptional = boardRepository.findById(boardAttackId)

        if (boardOptional.isEmpty) {
            logger.warn("Board not found")
            throw Exception("Board not found")
            // TODO create not found exception
        }

        val boardAttack = boardOptional.get().toVO()

        logger.info("START - player: ${attackingPlayer.username} attacking board: $boardAttackId")

        // TODO valid coordinate

        var coordinate = boardAttack.plays[coordinateAttack.axisX][coordinateAttack.axisY]

        // TODO add novo campo a coordinate para representar que atingiu um embarcação inimiga

        coordinate = coordinate.copy(
            gotHit = true,
            wasHitBy = attackingPlayer.username
        )

        boardAttack.plays[coordinateAttack.axisX][coordinateAttack.axisY] = coordinate

        boardRepository.saveAndFlush(
            boardAttack.toEntity()
        )

        return boardAttack.player
    }

    private fun populateInitialPlays(): MutableList<MutableList<Coordinate>> {
        // TODO create constant for number of line and column in board
        return MutableList(6) { axisX ->
            MutableList(6) { axisY -> Coordinate(axisX, axisY) }
        }
    }

    private fun mockInitialPosition(playerUsername: String): MutableList<Coordinate> {
        return MutableList(6) {
            (it <= 3)
            Coordinate(
                axisX = 5,
                axisY = it,
                occupiedByPlayer = playerUsername,
                isOccupied = true
            )
        }
    }
}