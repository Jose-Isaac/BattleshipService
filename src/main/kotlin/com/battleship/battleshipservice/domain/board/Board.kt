package com.battleship.battleshipservice.domain.board

import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(
    name = "board"
)
@kotlinx.serialization.Serializable(with = BoardSerializer::class)
data class Board(
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    val id: UUID = UUID.randomUUID(),

    val plays: String,

    val player: String
)
