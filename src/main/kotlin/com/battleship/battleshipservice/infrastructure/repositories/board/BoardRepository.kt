package com.battleship.battleshipservice.infrastructure.repositories.board

import com.battleship.battleshipservice.domain.board.Board
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BoardRepository: JpaRepository<Board, UUID>