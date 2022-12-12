package cz.klement.model.dto

import java.time.Instant

data class User(
  val id: Int,
  val username: String,
  val password: String,
  val email: String,
  val firstName: String,
  val lastName: String,
  val createdAt: Instant,
  val updatedAt: Instant
)

