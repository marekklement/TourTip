package cz.klement.routes

import cz.klement.extensions.toUUID
import cz.klement.mapper.command.mapCommand
import cz.klement.mapper.response.mapResponse
import cz.klement.model.request.UserPasswordChangeRequest
import cz.klement.model.request.UserUpdateRequest
import cz.klement.service.api.UserService
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.usersAuth() {

  val userService by closestDI().instance<UserService>()

  get(USERS_PREFIX) {
    userService.findAll().map {
      it.mapResponse()
    }.also {
      call.respond(it)
    }
  }

  get(USERS_BY_ID) {
    val id = call.parameters["id"]?.toUUID() ?: throw BadRequestException("Id was not provided in a path!")
    userService.get(id).also {
      call.respond(it)
    }
  }

  put(USERS_BY_ID) {
    val id = call.parameters["id"]?.toUUID() ?: throw BadRequestException("Id was not provided in a path!")
    call.receive<UserUpdateRequest>().mapCommand(id)
      .run(userService::editUserDetails)
      .mapResponse()
      .also {
        call.respond(it)
      }
  }

  put(USERS_BY_ID_PWD_CHANGE) {
    val id = call.parameters["id"]?.toUUID() ?: throw BadRequestException("Id was not provided in a path!")
    call.receive<UserPasswordChangeRequest>().mapCommand(id)
      .run(userService::changePassword)
      .mapResponse()
      .also {
        call.respond(it)
      }
  }
}
