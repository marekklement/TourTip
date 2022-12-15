package cz.klement.model.command

import java.util.*

data class PredictionCreateCommand(
  val id: Int,
  val userId: UUID,
  val gameId: UUID,
  val homeScore: Int,
  val awayScore: Int
)
