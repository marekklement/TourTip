package cz.klement.routes.admin

import com.papsign.ktor.openapigen.route.application
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.auth.delete
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.tags
import cz.klement.enums.SwaggerTags
import cz.klement.model.param.IdParam
import cz.klement.plugins.adminTokenAuthorized
import cz.klement.constants.USERS_BY_ID
import cz.klement.service.api.UserService
import io.ktor.http.*
import io.ktor.server.auth.jwt.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun NormalOpenAPIRoute.usersAdmin() {
  with(application) {
    val userService by closestDI().instance<UserService>()

    adminTokenAuthorized {
      route(USERS_BY_ID).delete<IdParam, HttpStatusCode, JWTPrincipal> (
        info(
          summary = "Delete user"
        ),
        tags(SwaggerTags.USERS)
      ){ params ->
        userService.delete(params.id)
        respond(HttpStatusCode.NoContent)
      }
    }

  }
}
