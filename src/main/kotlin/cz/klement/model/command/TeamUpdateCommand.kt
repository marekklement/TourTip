package cz.klement.model.command

import java.util.*

data class TeamUpdateCommand(
  val id: UUID,
  val name: String?
)
