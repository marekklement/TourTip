package cz.klement.model.dto

import java.time.Instant

data class Winner(
  val id: Int,
  val user: User,
  val tournament: Tournament,
  val points: Int,
  val createdAt: Instant,
  val updatedAt: Instant
)
