package cz.klement.model.request

data class UserUpdateRequest(
  val email: String?,
  val firstName: String?,
  val lastName: String?
)
