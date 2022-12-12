package cz.klement.model.command

data class TeamUpdateCommand(
  val id: Int,
  val name: String?
)
