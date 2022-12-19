package cz.klement.mapper.command

import cz.klement.extensions.hashPassword
import cz.klement.extensions.toPrettyString
import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserLoginCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.model.request.UserCreateRequest
import cz.klement.model.request.UserLoginRequest
import cz.klement.model.request.UserPasswordChangeRequest
import cz.klement.model.request.UserUpdateRequest
import io.ktor.server.config.*
import io.ktor.server.plugins.*
import java.util.UUID

fun UserCreateRequest.mapCommand() =
  checkValidity(this).run {
    UserCreateCommand(
      email = email ?: "",
      password = password?.hashPassword() ?: "",
      username = username ?: "",
      firstName = firstName ?: "",
      lastName = lastName ?: "",
    )
  }

fun UserLoginRequest.mapCommand(config: ApplicationConfig) =
  checkValidity(this).run {
    UserLoginCommand(
      username = username ?: "",
      password = password ?: "",
      config = config
    )
  }


fun UserPasswordChangeRequest.mapCommand(id: UUID) =
  UserUpdateCommand(
    id = id,
    password = password,
    email = null,
    firstName = null,
    lastName = null
  )

fun UserUpdateRequest.mapCommand(id: UUID) =
  UserUpdateCommand(
    id = id,
    password = null,
    email = email,
    firstName = firstName,
    lastName = lastName
  )

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

private fun checkValidity(request: UserLoginRequest) {
  mutableListOf<String>().apply {
    if (request.password.isNullOrEmpty()) add(request::password.name)
    if (request.username.isNullOrEmpty()) add(request::username.name)
  }.run {
    if (isNotEmpty()) throw BadRequestException("${toPrettyString()} fields are mandatory. Please provide it.")
  }
}
