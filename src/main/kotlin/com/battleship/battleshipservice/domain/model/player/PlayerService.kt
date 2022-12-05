package com.battleship.battleshipservice.domain.model.player

import com.battleship.battleshipservice.domain.player.Player

interface PlayerService {
    fun getPlayer(username: String): Player
}