package com.battleship.battleshipservice.domain.board

import java.util.UUID

data class Coordinate(
    val axisX: Int,
    val axisY: Int,
    val occupiedByUser: UUID,
    val isOccupied: Boolean = false,
    val gotHit: Boolean = false,
    val wasHitBy: UUID? = null
)
