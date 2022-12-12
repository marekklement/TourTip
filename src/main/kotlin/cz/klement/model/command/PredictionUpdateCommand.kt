package cz.klement.model.command

data class PredictionUpdateCommand(
  val id: Int,
  val userId: Int?,
  val gameId: Int?,
  val homeScore: Int?,
  val awayScore: Int?,
  val points: Int?,
)
