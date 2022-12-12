package cz.klement.model.command

data class UserCreateCommand(
  val username: String,
  val password: String,
  val email: String,
  val firstName: String,
  val lastName: String
)
