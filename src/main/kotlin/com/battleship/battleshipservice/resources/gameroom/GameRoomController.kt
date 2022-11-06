package com.jisaacbc.battleshipservice.resources.gameroom

import com.jisaacbc.battleshipservice.resources.gameroom.representation.NewGameRoomRepresentation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["/game-room"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class GameRoomController {

    @PostMapping("/new")
    fun newGameRoom(
        @RequestBody gameRoomRepresentation: NewGameRoomRepresentation
    ): String {
        return gameRoomRepresentation.name
    }
}
