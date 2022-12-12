package cz.klement.tables

import cz.klement.dao.WinnersDao
import cz.klement.extensions.mapRowToWinner
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.WinnerCreateCommand
import cz.klement.model.command.WinnerUpdateCommand
import cz.klement.model.dto.Winner
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Instant

object Winners : Table(), WinnersDao {
  val id = integer("id").autoIncrement()
  val userId = integer("user_id")
    .references(Users.id)
    .nullable()
  val tournamentId = integer("tournament_id")
    .references(Tournaments.id)
    .nullable()
  val points = integer("points")
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  override val primaryKey = PrimaryKey(id, name = "PK_Winners_Id")

  override fun get(winnerId: Int) =
    select {
      (id eq winnerId)
    }.mapNotNull {
      it.mapRowToWinner()
    }.singleOrNull()

  override fun findAll(): List<Winner> = selectAll().mapNotNull { it.mapRowToWinner() }

  override fun create(command: WinnerCreateCommand) =
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[userId] = command.userId
        it[tournamentId] = command.tournamentId
        it[points] = 0
        it[createdAt] = now
        it[updatedAt] = now
      })[id]
    }

  override fun edit(command: WinnerUpdateCommand) =
    update({ id eq command.id }) { winner ->
      command.userId?.let { winner[userId] = it }
      command.tournamentId?.let { winner[tournamentId] = it }
      command.points?.let { winner[points] = it }
      winner[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }

  override fun delete(winnerId: Int) = deleteWhere { (id eq winnerId) } > 0
}
