package com.battleship.battleshipservice.domain.board


@kotlinx.serialization.Serializable
data class BoardVO(
    val id: String,
    val plays: MutableList<MutableList<Coordinate>>,
    val player: String
)