package cz.klement.mapper.response

import cz.klement.extensions.toZonedInstant
import cz.klement.model.response.UserResponse
import cz.klement.tables.User

fun User.mapResponse() =
  UserResponse(
    id = id.value,
    username = username,
    password = password,
    email = email,
    firstName = firstName,
    lastName = lastName,
    createdAt = createdAt.toZonedInstant(),
    updatedAt = updatedAt.toZonedInstant()
  )
