package cz.klement.routes.basic

import com.papsign.ktor.openapigen.route.application
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.normal.post
import cz.klement.mapper.command.mapCommand
import cz.klement.model.request.UserCreateRequest
import cz.klement.model.request.UserLoginRequest
import cz.klement.constants.USERS_LOGIN
import cz.klement.constants.USERS_PREFIX
import cz.klement.service.api.UserService
import io.ktor.http.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.tags
import cz.klement.enums.SwaggerTags

fun NormalOpenAPIRoute.users() {
  with(application) {
    val userService by closestDI().instance<UserService>()

    route(USERS_PREFIX) {
      post<Unit, HttpStatusCode, UserCreateRequest> (
        info(
          summary = "Register new user"
        ),
        tags(SwaggerTags.USERS_AUTH)
      ){ _, request ->
        request.mapCommand()
          .run(userService::register)
        respond(HttpStatusCode.Created)
      }
    }


    route(USERS_LOGIN).post<Unit, UserLoginResponse, UserLoginRequest> (
      info(
        summary = "Login to account for user"
      ),
      tags(SwaggerTags.USERS_AUTH)
    ){ _, request ->
      request.mapCommand(environment.config)
        .run(userService::login)
        .let {
          UserLoginResponse(
            token = it
          )
        }.also { respond(it) }
    }
  }



}

data class UserLoginResponse(
  val token: String?
)
