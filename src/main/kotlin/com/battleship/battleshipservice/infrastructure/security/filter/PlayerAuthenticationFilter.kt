package com.battleship.battleshipservice.infrastructure.security.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.battleship.battleshipservice.domain.player.Credential
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.streams.toList

class PlayerAuthenticationFilter() : UsernamePasswordAuthenticationFilter() {

    constructor(authenticationManager: AuthenticationManager) : this() {
        this.authenticationManager = authenticationManager
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val (username, password) = ObjectMapper().readValue(request?.inputStream, Credential::class.java)
//        val username = request?.getParameter("username")
//        val password = request?.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authentication: Authentication?
    ) {
        val user: User = authentication?.principal as User
        val algorithm: Algorithm = Algorithm.HMAC256("secret".toByteArray())
        val accessToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withIssuer(request?.requestURI.toString())
            .withClaim("roles", user.authorities.stream().map(GrantedAuthority::getAuthority).toList())
            .sign(algorithm)
        val refreshToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000))
            .withIssuer(request?.requestURI.toString())
            .sign(algorithm)
        response?.setHeader("access_token", accessToken)
        response?.setHeader("refresh_token", refreshToken)
        response?.setHeader("Access-Control-Allow-Origin", "*")
    }
//    init {
//        setFilterProcessesUrl("/players/signin")
//    }
//
//    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
//        try {
//            val (email, password) = ObjectMapper().readValue(request.inputStream, Credential::class.java)
//            val token = UsernamePasswordAuthenticationToken(email, password, arrayListOf())
//            return authManager.authenticate(token)
//        } catch (e: Exception) {
//            throw UsernameNotFoundException("")
//        }
//    }
//
//    override fun successfulAuthentication(
//        request: HttpServletRequest?,
//        response: HttpServletResponse,
//        chain: FilterChain?,
//        authResult: Authentication
//    ) {
//        val username = (authResult.principal as UserDetailsImp).username
//        val token = jwtUtil.generateToken(username)
//        return response.addHeader("Authorization", "Bearer $token")
//    }
}