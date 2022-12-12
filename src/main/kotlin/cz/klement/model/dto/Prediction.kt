package cz.klement.model.dto

import java.time.Instant

data class Prediction(
  val id: Int,
  val user: User,
  val game: Game,
  val homeScore: Int,
  val awayScore: Int,
  val points: Int,
  val createdAt: Instant,
  val updatedAt: Instant
)
