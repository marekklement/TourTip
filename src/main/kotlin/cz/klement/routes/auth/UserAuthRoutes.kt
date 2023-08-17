package cz.klement.routes.auth

import com.papsign.ktor.openapigen.route.application
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.auth.get
import com.papsign.ktor.openapigen.route.path.auth.put
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.tags
import cz.klement.enums.SwaggerTags
import cz.klement.mapper.command.mapCommand
import cz.klement.mapper.response.mapResponse
import cz.klement.model.param.IdParam
import cz.klement.model.request.UserPasswordChangeRequest
import cz.klement.model.request.UserUpdateRequest
import cz.klement.model.response.UserResponse
import cz.klement.plugins.userTokenAuthorized
import cz.klement.constants.USERS_BY_ID
import cz.klement.constants.USERS_BY_ID_PWD_CHANGE
import cz.klement.constants.USERS_PREFIX
import cz.klement.service.api.UserService
import io.ktor.server.auth.jwt.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun NormalOpenAPIRoute.usersAuth() {
  with(application) {
    val userService by closestDI().instance<UserService>()

    userTokenAuthorized {
      route(USERS_PREFIX) {
        get<Unit, List<UserResponse>, JWTPrincipal> (
          info(
            summary = "Fetch all users"
          ),
          tags(SwaggerTags.USERS)
        ){
          userService.findAll().map {
            it.mapResponse()
          }.also {
            respond(it)
          }
        }
      }

      route(USERS_BY_ID).get<IdParam, UserResponse, JWTPrincipal> (
        info(
          summary = "Get one user"
        ),
        tags(SwaggerTags.USERS)
      ){ params ->
        userService.get(params.id)
          .mapResponse()
          .also {
            respond(it)
          }
      }

      route(USERS_BY_ID).put<IdParam, UserResponse, UserUpdateRequest, JWTPrincipal> (
        info(
          summary = "Edit user detail"
        ),
        tags(SwaggerTags.USERS)
      ){ params, request ->
        request.mapCommand(params.id)
          .run(userService::editUserDetails)
          .mapResponse()
          .also {
            respond(it)
          }
      }

      route(USERS_BY_ID_PWD_CHANGE).put<IdParam, UserResponse, UserPasswordChangeRequest, JWTPrincipal> (
        info(
          summary = "Change user password"
        ),
        tags(SwaggerTags.USERS)
      ){ params, request ->
        request.mapCommand(params.id)
          .run(userService::changePassword)
          .mapResponse()
          .also {
            respond(it)
          }
      }
    }

  }

}
