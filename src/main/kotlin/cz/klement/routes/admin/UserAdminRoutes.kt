package cz.klement.routes

import cz.klement.extensions.toUUID
import cz.klement.service.api.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.usersAdmin() {

  val userService by closestDI().instance<UserService>()

  delete(USERS_BY_ID) {
    val id = call.parameters["id"]?.toUUID() ?: throw BadRequestException("Id was not provided in a path!")
    userService.delete(id).also {
      call.respond(HttpStatusCode.OK)
    }
  }
}
