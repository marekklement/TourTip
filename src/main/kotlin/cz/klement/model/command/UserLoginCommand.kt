package cz.klement.model.command

import io.ktor.server.config.*

data class UserLoginCommand(
  val username: String,
  val password: String,
  val config: ApplicationConfig
)
