package cz.klement

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import cz.klement.plugins.*
import cz.klement.tools.configureDatabase
import cz.klement.tools.initServices
import org.kodein.di.ktor.di

fun main() {
  embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
    .start(wait = true)
}

fun Application.module() {
  configureSerialization()
  configureDatabase()
  di {
    initServices()
  }
  configureRouting()
}
