package com.jisaacbc.battleshipservice.infrastructure.repositories.gameroom

import com.battleship.battleshipservice.domain.gameroom.GameRoom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GameRoomRepository : JpaRepository<GameRoom, UUID>
