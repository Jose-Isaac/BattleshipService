package com.battleship.battleshipservice.domain.playergameroom

import java.io.Serializable
import java.util.*

class PlayerGameRoomId() : Serializable {
    lateinit var gameRoomId: UUID
    lateinit var playerId: UUID

    constructor(playerId: UUID, gameRoomId: UUID) : this() {
        this.playerId = playerId
        this.gameRoomId = gameRoomId
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerGameRoomId

        if (playerId != other.playerId) return false
        if (gameRoomId != other.gameRoomId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = playerId.hashCode()
        result = 31 * result + gameRoomId.hashCode()
        return result
    }
}
