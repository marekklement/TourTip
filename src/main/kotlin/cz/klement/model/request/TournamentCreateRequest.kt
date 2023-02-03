package cz.klement.model.request

import java.time.Instant

data class TournamentCreateRequest(
  val name: String?,
  val startDate: Instant?,
  val endDate: Instant?,
  val games: List<GameCreateRequest> = emptyList()
)
