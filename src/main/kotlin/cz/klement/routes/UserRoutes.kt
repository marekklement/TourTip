package cz.klement.routes

import cz.klement.model.command.UserCreateCommand
import cz.klement.service.api.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.users() {

  val userService by closestDI().instance<UserService>()

  get("users") {
    val users = userService.findAll()
    call.respond(users)
  }

  post("user") {
    call.receive<UserCreateCommand>().run {
      userService.register(this)
      call.respond(HttpStatusCode.Accepted)
    }
  }
}
