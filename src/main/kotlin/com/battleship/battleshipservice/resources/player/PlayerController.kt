package com.battleship.battleshipservice.resources.player

import com.battleship.battleshipservice.domain.player.Player
import com.battleship.battleshipservice.domain.player.toEntity
import com.battleship.battleshipservice.infrastructure.repositories.player.PlayerRepository
import com.battleship.battleshipservice.infrastructure.security.utils.getCurrentPlayerInContext
import com.battleship.battleshipservice.resources.player.representation.PlayerProfileRepresentation
import com.battleship.battleshipservice.resources.player.representation.PlayerRepresentation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@RestController
@RequestMapping(
    path = ["/player"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class PlayerController(
    val playerRepository: PlayerRepository
) {

    @PostMapping("/create")
    fun create(
        @RequestBody playerRepresentation: PlayerRepresentation
    ): Player = playerRepository.save(playerRepresentation.toEntity())

    @GetMapping("/profile")
    fun profile(): PlayerProfileRepresentation {
        val player = playerRepository.findByUsername(getCurrentPlayerInContext())
        return PlayerProfileRepresentation(player.get().username)
    }

}

