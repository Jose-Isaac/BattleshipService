package com.battleship.battleshipservice.domain.player

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(
    name = "player"
)
data class Player(
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    val id: UUID,

    val name: String
)
