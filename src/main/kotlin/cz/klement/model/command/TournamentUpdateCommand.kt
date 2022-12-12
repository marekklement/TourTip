package cz.klement.model.command

import java.time.Instant

data class TournamentUpdateCommand(
  val id: Int,
  val name: String?,
  val startDate: Instant?,
  val endDate: Instant?
)
