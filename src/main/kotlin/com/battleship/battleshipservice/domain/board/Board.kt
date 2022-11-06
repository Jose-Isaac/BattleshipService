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
data class Board(
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    val id: UUID,

    val gameRoomId: UUID,

    @Type(type = "jsonb")
    @Column(columnDefinition = "JSONB")
    val plays: List<Coordinate> = listOfNotNull()
)
