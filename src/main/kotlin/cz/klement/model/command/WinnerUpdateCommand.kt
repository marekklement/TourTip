package cz.klement.model.command

data class WinnerUpdateCommand(
  val id: Int,
  val userId: Int?,
  val tournamentId: Int?,
  val points: Int?,
)
