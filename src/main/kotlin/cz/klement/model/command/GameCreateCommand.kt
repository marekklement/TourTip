package cz.klement.model.command

import java.time.Instant
import java.util.*

data class GameCreateCommand(
  val tournamentId: UUID,
  val homeTeamId: UUID,
  val awayTeamId: UUID,
  val startTime: Instant,
  val homeScore: Int?,
  val awayScore: Int?,
)
