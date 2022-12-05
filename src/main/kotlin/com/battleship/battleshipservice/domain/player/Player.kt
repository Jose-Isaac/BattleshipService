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
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true, length = 20)
    val username: String,

    val password: String,
)
