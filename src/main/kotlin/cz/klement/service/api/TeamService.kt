package cz.klement.service.api

import cz.klement.model.command.TeamCreateCommand
import cz.klement.model.command.TeamImportCommand
import cz.klement.model.command.TeamUpdateCommand
import cz.klement.tables.Team
import java.util.UUID

interface TeamService {
  fun create(command: TeamCreateCommand): Team
  fun import(command: TeamImportCommand): List<Team>
  fun edit(command: TeamUpdateCommand): Team
  fun delete(id: UUID)
  fun get(id: UUID): Team
  fun findAll(): List<Team>
}
