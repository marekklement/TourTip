package cz.klement.plugins

import cz.klement.serialization.InstantAdapter
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import java.time.Instant

fun Application.configureSerialization() {
  install(ContentNegotiation) {
    gson {
      setPrettyPrinting()
      disableHtmlEscaping()
      registerTypeAdapter(Instant::class.java, InstantAdapter)
    }
  }

  routing {
  }
}
