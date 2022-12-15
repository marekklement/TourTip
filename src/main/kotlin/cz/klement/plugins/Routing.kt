package cz.klement.plugins

import cz.klement.routes.users
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

  routing {
    get("/") {
      call.respondText("Hello World!")
    }

    route("/api/v1") {
      users()
    }
  }
}
