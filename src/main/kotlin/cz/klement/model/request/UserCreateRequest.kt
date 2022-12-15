package cz.klement.model.request

data class UserCreateRequest(
  val username: String?,
  val password: String?,
  val email: String?,
  val firstName: String?,
  val lastName: String?
)
