package cz.klement.mapper.command

import cz.klement.extensions.toPrettyString
import cz.klement.model.command.UserCreateCommand
import cz.klement.model.request.UserCreateRequest
import io.ktor.server.plugins.*

fun UserCreateRequest.mapCommand() =
  checkValidity(this).run {
    UserCreateCommand(
      email = email ?: "",
      password = password ?: "",
      username = username ?: "",
      firstName = firstName ?: "",
      lastName = lastName ?: "",
    )
  }

private fun checkValidity(request: UserCreateRequest) {
  mutableListOf<String>().apply {
    if (request.email.isNullOrEmpty()) add(request::email.name)
    if (request.password.isNullOrEmpty()) add(request::password.name)
    if (request.username.isNullOrEmpty()) add(request::username.name)
    if (request.firstName.isNullOrEmpty()) add(request::firstName.name)
    if (request.lastName.isNullOrEmpty()) add(request::lastName.name)
  }.run {
    if (isNotEmpty()) throw BadRequestException("${toPrettyString()} fields are mandatory. Please provide it.")
  }
}
