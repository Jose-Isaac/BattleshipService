package com.battleship.battleshipservice.infrastructure.security.utils

import org.springframework.security.core.context.SecurityContextHolder

fun getCurrentPlayerInContext() = SecurityContextHolder.getContext().authentication.principal as String