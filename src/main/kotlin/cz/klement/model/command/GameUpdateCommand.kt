package cz.klement.model.command

import java.time.Instant
import java.util.*

data class GameUpdateCommand(
  val id: UUID,
  val startTime: Instant?,
  val homeScore: Int?,
  val awayScore: Int?,
)
