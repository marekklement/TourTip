package cz.klement.model.request

import java.time.Instant
import java.util.*

data class GameCreateRequest(
  val homeTeamId: UUID?,
  val awayTeamId: UUID?,
  val startTime: Instant?
)
