package cz.klement.model.dto

import java.time.Instant

data class Team(
  val id: Int,
  val name: String,
  val createdAt: Instant,
  val updatedAt: Instant
)
