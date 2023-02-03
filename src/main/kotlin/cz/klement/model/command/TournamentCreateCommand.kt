package cz.klement.model.command

import java.time.Instant

data class TournamentCreateCommand(
  val name: String,
  val startDate: Instant,
  val endDate: Instant,
  val games: List<TournamentGameCreateCommand>
)
