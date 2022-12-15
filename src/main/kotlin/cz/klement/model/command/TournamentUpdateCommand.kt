package cz.klement.model.command

import java.time.Instant
import java.util.*

data class TournamentUpdateCommand(
  val id: UUID,
  val name: String?,
  val startDate: Instant?,
  val endDate: Instant?
)
