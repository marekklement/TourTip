package cz.klement.model.command

data class UserUpdateCommand(
  val id: Int,
  val username: String?,
  val email: String?,
  val firstName: String?,
  val lastName: String?
)
