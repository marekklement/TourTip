package cz.klement.tables.dao

import cz.klement.model.command.SearchCommand
import cz.klement.model.command.TeamCreateCommand
import cz.klement.model.command.TeamUpdateCommand
import cz.klement.model.structures.PageResult
import cz.klement.tables.Team
import java.util.*

interface TeamsDao {
  fun get(teamId: UUID): Team?
  fun findAll(): List<Team>
  fun create(command: TeamCreateCommand): UUID
  fun edit(command: TeamUpdateCommand): Team?
  fun delete(teamId: UUID): Boolean
  fun search(command: SearchCommand): PageResult<Team>
}
