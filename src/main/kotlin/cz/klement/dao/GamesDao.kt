package cz.klement.dao

import cz.klement.model.command.GameCreateCommand
import cz.klement.model.command.GameUpdateCommand
import cz.klement.model.dto.Game

interface GamesDao {
  fun get(gameId: Int): Game?
  fun findAll(): List<Game>
  fun create(command: GameCreateCommand): Int
  fun edit(command: GameUpdateCommand): Game?
  fun delete(gameId: Int): Boolean
}
