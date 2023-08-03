package cz.klement

import cz.klement.plugins.*
import cz.klement.tools.configureDatabase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.kodein.di.ktor.di

fun main(args: Array<String>) {
  embeddedServer(Netty, commandLineEnvironment(args))
    .start(wait = true)
}

@Suppress("unused")
fun Application.module() {
  configureOpenApi()
  configureSerialization()
  configureDatabase()
  di {
    initInjections()
  }
  configureAuthentication()
  configureRouting()
  configureCors()
}
