package cz.klement.auth

import com.papsign.ktor.openapigen.model.Described
import com.papsign.ktor.openapigen.model.security.HttpSecurityScheme
import com.papsign.ktor.openapigen.model.security.SecuritySchemeModel
import com.papsign.ktor.openapigen.model.security.SecuritySchemeType
import com.papsign.ktor.openapigen.modules.providers.AuthProvider
import com.papsign.ktor.openapigen.route.path.auth.OpenAPIAuthenticatedRoute
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import cz.klement.plugins.ADMIN_AUTH
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.util.pipeline.*

class JwtAdminProvider : AuthProvider<JWTPrincipal> {

  enum class Scopes(override val description: String) : Described

  override val security: Iterable<Iterable<AuthProvider.Security<*>>> =
    listOf(
      listOf(
        AuthProvider.Security(
          SecuritySchemeModel(
            referenceName = "auth-token",
            type = SecuritySchemeType.http,
            scheme = HttpSecurityScheme.bearer,
            bearerFormat = "JWT",
            name = "Authorization"
          ),
          emptyList<Scopes>()
        )
      )
    )

  override suspend fun getAuth(pipeline: PipelineContext<Unit, ApplicationCall>): JWTPrincipal =
    pipeline.context.principal() ?: throw RuntimeException("No JWTPrincipal")

  override fun apply(route: NormalOpenAPIRoute): OpenAPIAuthenticatedRoute<JWTPrincipal> {
    val authenticatedKtorRoute = route.ktorRoute.authenticate(ADMIN_AUTH) {}
    return OpenAPIAuthenticatedRoute(authenticatedKtorRoute, route.provider.child(), this)
  }
}
