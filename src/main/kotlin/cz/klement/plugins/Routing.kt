package cz.klement.plugins

import cz.klement.routes.users
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

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
