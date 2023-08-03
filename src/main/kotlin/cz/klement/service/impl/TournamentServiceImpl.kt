package cz.klement.service.impl

import cz.klement.mapper.command.mapCommand
import cz.klement.model.command.TournamentCreateCommand
import cz.klement.model.command.TournamentUpdateCommand
import cz.klement.service.api.GameService
import cz.klement.service.api.TournamentService
import cz.klement.tables.Tournament
import cz.klement.tables.Tournaments
import io.ktor.server.plugins.*
import java.util.*

class TournamentServiceImpl(
  private val gameService: GameService
) : TournamentService {

  override fun create(command: TournamentCreateCommand) =
    Tournaments.create(command).run {
      command.games.forEach {
        gameService.create(it.mapCommand(this))
      }
      get(this)
    }

  override fun edit(command: TournamentUpdateCommand) =
    Tournaments.edit(command) ?: throw NotFoundException("Tournament with id ${command.id} cannot be found - no update processed!")

  override fun delete(id: UUID) {
    Tournaments.delete(id)
  }

  override fun get(id: UUID): Tournament = Tournaments.get(id) ?: throw NotFoundException("Tournament with id $id not found!")
}
