package cz.klement.plugins

import com.auth0.jwt.interfaces.Payload
import cz.klement.auth.getJwtVerifier
import cz.klement.enums.UserRole
import cz.klement.extensions.getJwtRealm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

const val USER_AUTH = "auth-jwt"
const val ADMIN_AUTH = "auth-jwt-admin"

fun Application.configureAuthentication() {
  val config = environment.config
  install(Authentication) {
    jwt(USER_AUTH) {
      realm = config.getJwtRealm()
      verifier(getJwtVerifier(config))
      validate { credential ->
        validateRole(role = UserRole.USER.toString(), payload = credential.payload)
      }
    }
    jwt(ADMIN_AUTH) {
      realm = config.getJwtRealm()
      verifier(getJwtVerifier(config))
      validate { credential ->
        validateRole(role = UserRole.ADMIN.toString(), payload = credential.payload)
      }
    }
  }
}

private fun validateRole(role: String, payload: Payload): JWTPrincipal? {
  val roles = payload.getClaim("roles").asString()
  val username = payload.getClaim("username").asString()
  return if (!roles.isNullOrEmpty() || !username.isNullOrEmpty()) {
    if (roles.contains(role)) {
      JWTPrincipal(payload)
    } else null
  } else null
}

