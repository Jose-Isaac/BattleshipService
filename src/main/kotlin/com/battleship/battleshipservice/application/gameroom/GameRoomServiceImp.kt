package com.battleship.battleshipservice.application.gameroom

import com.battleship.battleshipservice.domain.board.BoardVO
import com.battleship.battleshipservice.domain.board.Coordinate
import com.battleship.battleshipservice.domain.board.toVO
import com.battleship.battleshipservice.domain.gameroom.*
import com.battleship.battleshipservice.domain.model.board.BoardService
import com.battleship.battleshipservice.domain.model.player.PlayerService
import com.battleship.battleshipservice.domain.playergameroom.PlayersGameRoom
import com.battleship.battleshipservice.infrastructure.repositories.playergameroom.PlayerGameRoomRepository
import com.battleship.battleshipservice.infrastructure.security.utils.getCurrentPlayerInContext
import com.battleship.battleshipservice.domain.model.gameroom.GameRoomService
import com.battleship.battleshipservice.domain.player.ActivityType
import com.battleship.battleshipservice.domain.player.Player
import com.battleship.battleshipservice.infrastructure.repositories.board.BoardRepository
import com.battleship.battleshipservice.infrastructure.repositories.gameroom.GameRoomRepository
import com.battleship.battleshipservice.resources.gameroom.representation.GameRoomRepresentation
import com.battleship.battleshipservice.resources.gameroom.representation.JoinRepresentation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GameRoomServiceImp(
    private val gameRoomRepository: GameRoomRepository,
    private val boardRepository: BoardRepository,
    private val playerService: PlayerService,
    private val boardService: BoardService,
    private val playerGameRoomRepository: PlayerGameRoomRepository
) : GameRoomService {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val pendingInvitations = mutableListOf<PendingInvitation>()

    override fun createInvitation(): UUID {
        val pendingInvitation = PendingInvitation(
            invitation = UUID.randomUUID(),
            playerUsername = getCurrentPlayerInContext()
        )
        pendingInvitations.add(pendingInvitation)
        return pendingInvitation.invitation
    }

    override fun removeInvitation(invitation: UUID) {
        pendingInvitations.removeIf { it.invitation == invitation }
    }

    override fun create(pendingInvitation: PendingInvitation, currentPlayer: String): GameRoomVO {
        logger.info("START - creating a new game room")

        val playerOne = playerService.getPlayer(currentPlayer)
        val playerTwo = playerService.getPlayer(pendingInvitation.playerUsername)

        val boardForPlayerOne = boardService.create(playerOne.username)
        val boardForPlayerTwo = boardService.create(playerTwo.username)

        val gameRoom = gameRoomRepository.save(
            GameRoom(
                boardPlayerOne = UUID.fromString(boardForPlayerOne.id),
                boardPlayerTwo = UUID.fromString(boardForPlayerTwo.id),
                playerTurn = playerOne.username // TODO create sort initial player
            )
        )
        logger.info("END - created a new game room")
        return GameRoomVO(
            id = gameRoom.id,
            boardForPlayerOne,
            boardForPlayerTwo,
            playerTurn = playerOne.username
        )
    }

    override fun join(joinRepresentation: JoinRepresentation): GameRoomRepresentation {
        val pendingInvitation = pendingInvitations.find { it.invitation == UUID.fromString(joinRepresentation.inviteId) }

        if (pendingInvitation == null) {
            logger.warn("Invitation: ${joinRepresentation.inviteId} not found")
            throw Exception("Invitation: ${joinRepresentation.inviteId} not found")
            // TODO create not found exception
        }

        val gameRoom = create(pendingInvitation, joinRepresentation.username)

        pendingInvitations.remove(pendingInvitation)

        logger.info("Response representation: ${gameRoom.toRepresentation(ActivityType.JOIN)}")

        return gameRoom.toRepresentation(ActivityType.JOIN)
    }

    override fun attackMove(
        gameRoomId: UUID,
        boardAttackId: UUID,
        attackingPlayer: String,
        coordinate: Coordinate
    ): GameRoomRepresentation {
        val gameRoom = gameRoomRepository.findById(gameRoomId)

        if (gameRoom.isEmpty) {
            logger.warn("Game room not found")
            throw Exception("Game room not found")
            // TODO create not found exception
        }

        val player = playerService.getPlayer(attackingPlayer)

        if (gameRoom.get().playerTurn != player.username) {
            logger.warn("This is not your turn play")
            throw Exception("This is not your turn play")
            // TODO create exception
        }

        val playerTurn = boardService.attackMove(boardAttackId, coordinate, player) // TODO return board

        val boardPlayerOne = boardRepository.findById(gameRoom.get().boardPlayerOne).get().toVO()
        val boardPlayerTwo = boardRepository.findById(gameRoom.get().boardPlayerTwo).get().toVO()

        val hasWinner = checkWinner(boardPlayerOne, boardPlayerTwo)

        logger.info("has winner: $hasWinner")

        val gameRoomUpdated = if (hasWinner != null ) {
            gameRoomRepository.save(
                gameRoom.get().copy(
                    winner = hasWinner
                )
            )
        } else {
            var turn = gameRoom.get().turn
            gameRoomRepository.save(
                gameRoom.get().copy(
                    playerTurn = playerTurn,
                    turn = ++turn
                )
            )
        }

        return gameRoomUpdated.toVO(
            boardPlayerOne,
            boardPlayerTwo
        ).toRepresentation(if (hasWinner == null) ActivityType.ATTACK else ActivityType.HAS_WINNER)
    }

    private fun addPlayerInGameRoom(gameRoom: GameRoom, player: Player) {
        // TODO validar se jã exite dois jogadores na sala
        logger.info("add player: ${player.username} in game room: ${gameRoom.id}")
        playerGameRoomRepository.save(PlayersGameRoom(player.id, gameRoom.id))
        logger.info("END - player: ${player.username} adding in game room: ${gameRoom.id}")
    }

    private fun checkWinner(boardPlayerOne: BoardVO, boardPlayerTwo: BoardVO): String? {
        return if (hasBeanDefeated(boardPlayerOne)) {
            boardPlayerTwo.player
        } else if (hasBeanDefeated(boardPlayerTwo)) {
            boardPlayerOne.player
        } else { null }
    }

    private fun hasBeanDefeated(board: BoardVO): Boolean {
        board.plays.forEach {
            line -> line.forEach { if (!it.gotHit) return false }
        }

        return true
    }
}