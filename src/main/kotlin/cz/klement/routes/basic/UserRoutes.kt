package cz.klement.routes.basic

import cz.klement.mapper.command.mapCommand
import cz.klement.model.request.UserCreateRequest
import cz.klement.model.request.UserLoginRequest
import cz.klement.routes.USERS_LOGIN
import cz.klement.routes.USERS_PREFIX
import cz.klement.service.api.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.users(config: ApplicationConfig) {

  val userService by closestDI().instance<UserService>()

  post(USERS_PREFIX) {
    call.receive<UserCreateRequest>().mapCommand()
      .run(userService::register)
      .also {
        call.respond(HttpStatusCode.Accepted)
      }
  }

  post(USERS_LOGIN) {
    call.receive<UserLoginRequest>().mapCommand(config)
      .run(userService::login)
      .also {
        call.respond(hashMapOf("token" to it))
      }
  }


}
