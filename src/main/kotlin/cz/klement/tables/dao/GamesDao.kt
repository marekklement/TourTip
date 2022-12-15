package cz.klement.tables.dao

import cz.klement.model.command.GameCreateCommand
import cz.klement.model.command.GameUpdateCommand
import cz.klement.tables.Game
import java.util.*

interface GamesDao {
  fun get(gameId: UUID): Game?
  fun findAll(): List<Game>
  fun create(command: GameCreateCommand): UUID
  fun edit(command: GameUpdateCommand): Game?
  fun delete(gameId: UUID): Boolean
}
