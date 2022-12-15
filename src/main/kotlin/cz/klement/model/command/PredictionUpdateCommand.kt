package cz.klement.model.command

import java.util.*

data class PredictionUpdateCommand(
  val id: UUID,
  val userId: UUID?,
  val gameId: UUID?,
  val homeScore: Int?,
  val awayScore: Int?,
  val points: Int?,
)
