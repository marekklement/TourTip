package cz.klement.service.impl

import cz.klement.model.command.TeamCreateCommand
import cz.klement.model.command.TeamImportCommand
import cz.klement.model.command.TeamUpdateCommand
import cz.klement.service.api.TeamService
import cz.klement.tables.Team
import cz.klement.tables.Teams
import io.ktor.server.plugins.*
import java.util.*

class TeamServiceImpl : TeamService {
  override fun create(command: TeamCreateCommand): Team = Teams.create(command).run(::get)

  override fun import(command: TeamImportCommand): List<Team> = command.teamNames.map { TeamCreateCommand(it).run(::create) }

  override fun edit(command: TeamUpdateCommand): Team = Teams.edit(command) ?: throw NotFoundException("Team with id ${command.id} cannot be found - no update processed!")

  override fun delete(id: UUID) {
    Teams.delete(id)
  }

  override fun get(id: UUID): Team =
    Teams.get(id) ?: throw NotFoundException("Team with id $id not found!")

  override fun findAll(): List<Team> = Teams.findAll()
}
