package cz.klement.model.command

import java.util.*

data class WinnerCreateCommand(
  val userId: UUID,
  val tournamentId: UUID
)
