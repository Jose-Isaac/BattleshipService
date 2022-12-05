package com.battleship.battleshipservice.infrastructure.repositories.player

import com.battleship.battleshipservice.domain.player.Player
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface PlayerRepository: JpaRepository<Player, UUID> {
    fun findByUsername(username: String): Optional<Player>
}