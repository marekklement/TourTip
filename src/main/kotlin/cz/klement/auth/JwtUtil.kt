package cz.klement.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import cz.klement.extensions.getJwtAudience
import cz.klement.extensions.getJwtIssuer
import cz.klement.extensions.getJwtSecret
import cz.klement.tables.User
import io.ktor.server.config.*
import java.util.*

var verifier: JWTVerifier? = null

fun getJWTToken(config: ApplicationConfig, user: User): String {
  return JWT.create()
    .withAudience(config.getJwtAudience())
    .withIssuer(config.getJwtIssuer())
    .withClaim("username", user.username)
    .withClaim("roles", user.roles)
    .withExpiresAt(Date(System.currentTimeMillis() + 86400000))
    .sign(Algorithm.HMAC256(config.getJwtSecret()))
}

fun getJwtVerifier(config: ApplicationConfig): JWTVerifier {
  return verifier ?: JWT.require(Algorithm.HMAC256(config.getJwtSecret()))
    .withAudience(config.getJwtAudience())
    .withIssuer(config.getJwtIssuer())
    .build()
    .also { verifier = it }
}

