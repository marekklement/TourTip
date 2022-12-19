package cz.klement.model.command

import java.util.*

data class UserUpdateCommand(
  val id: UUID,
  val password: String?,
  val email: String?,
  val firstName: String?,
  val lastName: String?
)
