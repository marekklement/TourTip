package cz.klement.tables

import cz.klement.extensions.mapGame
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.GameCreateCommand
import cz.klement.model.command.GameUpdateCommand
import cz.klement.model.command.SearchCommand
import cz.klement.model.structures.PageResult
import cz.klement.tables.api.TableBase
import cz.klement.tables.dao.GamesDao
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

object Games : TableBase("games"), GamesDao {
  val tournament = reference("tournament_id", Tournaments)
  val homeTeam = reference("home_team_id", Teams)
  val awayTeam = reference("away_team_id", Teams)
  val startTime = datetime("start_time")
  val homeScore = integer("home_score").nullable()
  val awayScore = integer("away_score").nullable()

  override fun get(gameId: UUID) = transaction {
    Game.find {
      Games.id eq gameId
    }.singleOrNull()
  }


  override fun findAll(): List<Game> = transaction {
    Game.all().toList()
  }

  override fun findAllForTournament(tournamentId: UUID): List<Game> = Game.find { tournament eq tournamentId }.toList()

  override fun search(command: SearchCommand): PageResult<Game> {
    val totalCount = selectAll().count()
    val content = this.let { if (command.where != null) it.select(command.where) else it.selectAll() }
      .let { if (command.n != null) it.limit(n = command.n, offset = command.offset) else it }
      .toList()
      .map {
        it.mapGame()
      }
    return PageResult(
      content = content,
      pageNumber = command.offset,
      pageSize = command.n ?: content.size,
      totalCount = totalCount
    )
  }

  override fun create(command: GameCreateCommand) = transaction {
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[tournament] = command.tournamentId
        it[homeTeam] = command.homeTeamId
        it[awayTeam] = command.awayTeamId
        it[startTime] = command.startTime.toLocalDateTime()
        it[homeScore] = command.homeScore
        it[awayScore] = command.awayScore
        it[createdAt] = now
        it[updatedAt] = now
      })[Games.id].value
    }
  }


  override fun edit(command: GameUpdateCommand) = transaction {
    update({ Games.id eq command.id }) { game ->
      command.startTime?.let { game[startTime] = it.toLocalDateTime() }
      command.homeScore?.let { game[homeScore] = it }
      command.awayScore?.let { game[awayScore] = it }
      game[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }
  }


  override fun delete(gameId: UUID) = transaction {
    deleteWhere { (id eq gameId) } > 0
  }

}

class Game(id: EntityID<UUID>) : UUIDEntity(id) {
  companion object : UUIDEntityClass<Game>(Games)

  val tournament by Tournament referencedOn Games.tournament
  val homeTeam by Team referencedOn Games.homeTeam
  val awayTeam by Team referencedOn Games.awayTeam
  val startTime by Games.startTime
  val homeScore by Games.homeScore
  val awayScore by Games.awayScore
  val createdAt by Games.createdAt
  val updatedAt by Games.updatedAt
}
