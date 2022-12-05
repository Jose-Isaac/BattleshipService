package com.battleship.battleshipservice.domain.playergameroom

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table

@Entity
@Table(
    name = "player_game_room"
)
@IdClass(PlayerGameRoomId::class)
data class PlayersGameRoom(
    @Id
    val playerId: UUID,

    @Id
    val gameRoomId: UUID
)
