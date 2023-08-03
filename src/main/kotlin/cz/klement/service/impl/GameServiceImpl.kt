package cz.klement.service.impl

import cz.klement.model.command.GameCreateCommand
import cz.klement.model.command.GameUpdateCommand
import cz.klement.service.api.GameService
import cz.klement.tables.Game
import cz.klement.tables.Games
import io.ktor.server.plugins.*
import java.util.*

class GameServiceImpl : GameService {
  override fun create(command: GameCreateCommand): Game = Games.create(command).run(::get)

  override fun edit(command: GameUpdateCommand): Game = Games.edit(command) ?: throw NotFoundException("Game with id ${command.id} cannot be found - no update processed!")

  override fun delete(id: UUID) {
    Games.delete(id)
  }

  override fun get(id: UUID): Game =
    Games.get(id) ?: throw NotFoundException("Game with id $id not found!")

  override fun findAll(): List<Game> = Games.findAll()

  override fun findAllForTournament(tournamentId: UUID): List<Game> = Games.findAllForTournament(tournamentId)
}
