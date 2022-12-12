package cz.klement.model.command

data class PredictionCreateCommand(
  val id: Int,
  val userId: Int,
  val gameId: Int,
  val homeScore: Int,
  val awayScore: Int
)
