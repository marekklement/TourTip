package cz.klement.model.response

import java.time.Instant
import java.util.*

data class UserResponse(
  val id: UUID,
  val username: String,
  val email: String,
  val firstName: String,
  val lastName: String,
  val createdAt: Instant,
  val updatedAt: Instant
)
