package cz.klement.dao

import cz.klement.model.command.TeamCreateCommand
import cz.klement.model.command.TeamUpdateCommand
import cz.klement.model.dto.Team

interface TeamsDao {
  fun get(teamId: Int): Team?
  fun findAll(): List<Team>
  fun create(command: TeamCreateCommand): Int
  fun edit(command: TeamUpdateCommand): Team?
  fun delete(teamId: Int): Boolean
}
