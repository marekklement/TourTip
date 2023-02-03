package cz.klement.tables.dao

import cz.klement.model.command.SearchCommand
import cz.klement.model.command.TournamentCreateCommand
import cz.klement.model.command.TournamentUpdateCommand
import cz.klement.model.structures.PageResult
import cz.klement.tables.Tournament
import java.util.*

interface TournamentsDao {
  fun get(tournamentId: UUID): Tournament?
  fun findAll(): List<Tournament>
  fun create(command: TournamentCreateCommand): UUID
  fun edit(command: TournamentUpdateCommand): Tournament?
  fun delete(tournamentId: UUID): Boolean
  fun search(command: SearchCommand): PageResult<Tournament>
}
