package cz.klement.model.command

import java.time.Instant

data class GameUpdateCommand(
  val id: Int,
  val tournamentId: Int?,
  val homeTeamId: Int?,
  val awayTeamId: Int?,
  val startTime: Instant?,
  val homeScore: Int?,
  val awayScore: Int?,
)
