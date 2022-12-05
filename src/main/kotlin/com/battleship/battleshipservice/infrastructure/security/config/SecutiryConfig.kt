package com.battleship.battleshipservice.infrastructure.security.config

import com.battleship.battleshipservice.infrastructure.security.encodePassword
import com.battleship.battleshipservice.infrastructure.security.filter.PlayersAuthorizationFilter
import com.battleship.battleshipservice.infrastructure.security.filter.PlayerAuthenticationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsService)
                .passwordEncoder(encodePassword())
    }

    override fun configure(http: HttpSecurity) {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
//        corsConfiguration.allowedOrigins = listOf("http://localhost:3001")
        corsConfiguration.allowedOriginPatterns = listOf("*")
        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE")
        corsConfiguration.allowCredentials = true
        corsConfiguration.exposedHeaders = listOf(
            "Authorization",
            "Access-Control-Allow-Origin",
            "access_token",
            "refresh_token"
        )

        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests()
            .antMatchers("/token/refresh", "/player/create", "/login", "/ws/**", "/ws").permitAll()
//        http.authorizeRequests()
//            .antMatchers(HttpMethod.GET, "/players/**").hasAnyAuthority("ROLE_USER")
        http.authorizeRequests().anyRequest().authenticated()
        http.cors().configurationSource { corsConfiguration }
        http.addFilter(PlayerAuthenticationFilter(authenticationManager()))
        http.addFilterBefore(PlayersAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

//    @Bean
//    override fun authenticationManagerBean(): AuthenticationManager {
//        return super.authenticationManagerBean()
//    }
}