package cz.klement.model.dto

import java.time.Instant

data class Game(
  val id: Int,
  val tournament: Tournament,
  val homeTeam: Team,
  val awayTeam: Team,
  val startTime: Instant,
  val homeScore: Int?,
  val awayScore: Int?,
  val createdAt: Instant,
  val updatedAt: Instant
)
