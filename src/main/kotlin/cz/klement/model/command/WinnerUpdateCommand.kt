package cz.klement.model.command

import java.util.*

data class WinnerUpdateCommand(
  val id: UUID,
  val userId: UUID?,
  val tournamentId: UUID?,
  val points: Int?,
)
