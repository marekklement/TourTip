package cz.klement.tables

import cz.klement.dao.GamesDao
import cz.klement.extensions.mapRowToGame
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.GameCreateCommand
import cz.klement.model.command.GameUpdateCommand
import cz.klement.model.dto.Game
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Instant

object Games : Table(), GamesDao{
  val id = integer("id").autoIncrement()
  val tournamentId = integer("tournament_id")
    .references(Tournaments.id)
    .nullable()
  val homeTeamId = integer("home_team_id")
    .references(Teams.id)
    .nullable()
  val awayTeamId = integer("away_team_id")
    .references(Teams.id)
    .nullable()
  val startTime = datetime("start_time")
  val homeScore = integer("home_score").nullable()
  val awayScore = integer("away_score").nullable()
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  override val primaryKey = PrimaryKey(id, name = "PK_Games_Id")

  override fun get(gameId: Int) =
    select {
      (id eq gameId)
    }.mapNotNull {
      it.mapRowToGame()
    }.singleOrNull()

  override fun findAll(): List<Game> = selectAll().mapNotNull { it.mapRowToGame() }

  override fun create(command: GameCreateCommand) =
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[tournamentId] = command.tournamentId
        it[homeTeamId] = command.homeTeamId
        it[awayTeamId] = command.awayTeamId
        it[startTime] = command.startTime.toLocalDateTime()
        it[homeScore] = command.homeScore
        it[awayScore] = command.awayScore
        it[createdAt] = now
        it[updatedAt] = now
      })[id]
    }

  override fun edit(command: GameUpdateCommand) =
    update({ id eq command.id }) { game ->
      command.tournamentId?.let { game[tournamentId] = it }
      command.homeTeamId?.let { game[homeTeamId] = it }
      command.awayTeamId?.let { game[awayTeamId] = it }
      command.startTime?.let { game[startTime] = it.toLocalDateTime() }
      command.homeScore?.let { game[homeScore] = it }
      command.awayScore?.let { game[awayScore] = it }
      game[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }

  override fun delete(gameId: Int) = deleteWhere { (id eq gameId) } > 0

}
