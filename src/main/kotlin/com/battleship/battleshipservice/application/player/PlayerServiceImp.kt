package com.battleship.battleshipservice.application.player

import com.battleship.battleshipservice.domain.model.player.PlayerService
import com.battleship.battleshipservice.domain.player.Player
import com.battleship.battleshipservice.infrastructure.repositories.player.PlayerRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PlayerServiceImp(
    private val playerRepository: PlayerRepository
) : PlayerService, UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val player = playerRepository.findByUsername(username)

        if (player.isEmpty) {
            throw UsernameNotFoundException("Player not found!")
        }

        val authorities: List<SimpleGrantedAuthority> = emptyList()

        return User(player.get().username, player.get().password, authorities)
    }

    override fun getPlayer(username: String): Player {
        val optional = playerRepository.findByUsername(username)

        if (optional.isPresent) {
            return optional.get()
        }

        throw Exception("Player not found")
    }
}