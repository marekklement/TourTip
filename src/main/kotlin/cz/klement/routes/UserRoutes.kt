package cz.klement.routes

import cz.klement.extensions.toUUID
import cz.klement.mapper.command.mapCommand
import cz.klement.mapper.response.mapResponse
import cz.klement.model.request.UserCreateRequest
import cz.klement.service.api.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.users() {

  val userService by closestDI().instance<UserService>()

  get("users") {
    userService.findAll().map{
      it.mapResponse()
    }.also {
      call.respond(it)
    }
  }

  post("user") {
    call.receive<UserCreateRequest>().mapCommand()
      .run(userService::register)
      .also {
        call.respond(HttpStatusCode.Accepted)
      }
  }

  delete("user/{id}") {
    val id = call.parameters["id"]?.toUUID() ?: throw BadRequestException("Id was not provided in a path!")
    userService.delete(id).also {
      call.respond(HttpStatusCode.OK)
    }
  }
}
