package cz.klement.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCors() {
  install(CORS) {
    allowMethod(HttpMethod.Options)
    allowMethod(HttpMethod.Post)
    allowMethod(HttpMethod.Put)
    allowMethod(HttpMethod.Delete)
    allowMethod(HttpMethod.Get)
    allowHeader(HttpHeaders.AccessControlAllowOrigin)
    allowHeader(HttpHeaders.ContentType)
    allowHeader(HttpHeaders.Authorization)
    anyHost()
  }
}
