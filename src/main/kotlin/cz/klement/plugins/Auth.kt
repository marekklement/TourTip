package cz.klement.plugins

import com.auth0.jwt.interfaces.Payload
import com.papsign.ktor.openapigen.route.path.auth.OpenAPIAuthenticatedRoute
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.modules.providers.AuthProvider
import cz.klement.auth.JwtAdminProvider
import cz.klement.auth.JwtUserProvider
import cz.klement.auth.getJwtVerifier
import cz.klement.enums.UserRole
import cz.klement.extensions.getJwtRealm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.*
import javax.naming.AuthenticationException

const val USER_AUTH = "auth-jwt-user"
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
      challenge { _, _ ->
        call.respond(
          HttpStatusCode.Unauthorized,
          ErrorResponse(
            code = HttpStatusCode.Unauthorized.value,
            description = HttpStatusCode.Unauthorized.description,
            message = "This endpoint requires valid user access token."
          )
        )
      }
    }
    jwt(ADMIN_AUTH) {
      realm = config.getJwtRealm()
      verifier(getJwtVerifier(config))
      validate { credential ->
        validateRole(role = UserRole.ADMIN.toString(), payload = credential.payload)
      }
      challenge { _, _ ->
        call.respond(
          HttpStatusCode.Unauthorized,
          ErrorResponse(
            code = HttpStatusCode.Unauthorized.value,
            description = HttpStatusCode.Unauthorized.description,
            message = "This endpoint requires valid admin access token."
          )
        )
      }
    }
  }
}

@KtorDsl
inline fun NormalOpenAPIRoute.userTokenAuthorized(
  route: OpenAPIAuthenticatedRoute<JWTPrincipal>.() -> Unit
): OpenAPIAuthenticatedRoute<JWTPrincipal> = authorizedRoute(JwtUserProvider(), route)

@KtorDsl
inline fun NormalOpenAPIRoute.adminTokenAuthorized(
  route: OpenAPIAuthenticatedRoute<JWTPrincipal>.() -> Unit
): OpenAPIAuthenticatedRoute<JWTPrincipal> = authorizedRoute(JwtAdminProvider(), route)

@KtorDsl
inline fun <reified T : Principal> NormalOpenAPIRoute.authorizedRoute(
  provider: AuthProvider<T>,
  route: OpenAPIAuthenticatedRoute<T>.() -> Unit,
): OpenAPIAuthenticatedRoute<T> = provider.apply(this).apply(route)

private fun validateRole(role: String, payload: Payload): JWTPrincipal {
  val roles = payload.getClaim("roles").asString()
  val username = payload.getClaim("username").asString()
  return if (!roles.isNullOrEmpty() || !username.isNullOrEmpty()) {
    if (roles.contains(role)) {
      JWTPrincipal(payload)
    } else throw AuthenticationException("User does not have role $role!")
  } else throw AuthenticationException("User has no roles or Username was not found in token!")
}

data class ErrorResponse(
  val code: Int,
  val message: String,
  val description: String
)

