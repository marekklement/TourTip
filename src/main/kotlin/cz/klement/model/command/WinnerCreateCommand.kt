package cz.klement.model.command

data class WinnerCreateCommand(
  val userId: Int,
  val tournamentId: Int
)
