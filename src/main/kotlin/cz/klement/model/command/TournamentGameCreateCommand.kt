package cz.klement.model.command

import java.time.Instant
import java.util.*

data class TournamentGameCreateCommand(
  val homeTeamId: UUID,
  val awayTeamId: UUID,
  val startTime: Instant
)
