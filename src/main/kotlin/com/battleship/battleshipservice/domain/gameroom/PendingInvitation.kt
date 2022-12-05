package com.battleship.battleshipservice.domain.gameroom

import java.util.UUID

data class PendingInvitation(
    val invitation: UUID,
    val playerUsername: String
)
