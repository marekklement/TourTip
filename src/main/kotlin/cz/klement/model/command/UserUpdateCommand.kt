package cz.klement.model.command

import java.util.*

data class UserUpdateCommand(
  val id: UUID,
  val username: String?,
  val email: String?,
  val firstName: String?,
  val lastName: String?
)
