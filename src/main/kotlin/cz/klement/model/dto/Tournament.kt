package cz.klement.model.dto

import java.time.Instant

data class Tournament(
  val id: Int,
  val name: String,
  val startDate: Instant,
  val endDate: Instant,
  val createdAt: Instant,
  val updatedAt: Instant
)
