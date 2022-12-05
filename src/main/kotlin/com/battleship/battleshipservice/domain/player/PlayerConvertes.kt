package com.battleship.battleshipservice.domain.player

import com.battleship.battleshipservice.infrastructure.security.encodePassword
import com.battleship.battleshipservice.resources.player.representation.PlayerRepresentation

fun PlayerRepresentation.toEntity() = Player(
    username = this.username,
    password = encodePassword().encode(this.password)
)