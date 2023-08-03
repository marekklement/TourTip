package cz.klement.service.api

import cz.klement.model.command.GameCreateCommand
import cz.klement.model.command.GameUpdateCommand
import cz.klement.tables.Game
import java.util.UUID

interface GameService {
  fun create(command: GameCreateCommand): Game
  fun edit(command: GameUpdateCommand): Game
  fun delete(id: UUID)
  fun get(id: UUID): Game
  fun findAll(): List<Game>
  fun findAllForTournament(tournamentId: UUID): List<Game>
}
