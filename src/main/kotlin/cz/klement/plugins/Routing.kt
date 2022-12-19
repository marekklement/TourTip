package cz.klement.plugins

import cz.klement.routes.API_PREFIX
import cz.klement.routes.basic.users
import cz.klement.routes.usersAdmin
import cz.klement.routes.usersAuth
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

  val config = environment.config

  routing {
    get("/") {
      call.respondText("Welcome to Tournament Tip app!")
    }

    route(API_PREFIX) {
      users(config)
      authenticate(USER_AUTH) {
        usersAuth()
      }
      authenticate(ADMIN_AUTH) {
        usersAdmin()
      }
    }
    swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
  }
}
