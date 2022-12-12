package cz.klement.tables

import cz.klement.dao.TournamentsDao
import cz.klement.extensions.mapRowToTournament
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.TournamentCreateCommand
import cz.klement.model.command.TournamentUpdateCommand
import cz.klement.model.dto.Tournament
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Instant

object Tournaments : Table(), TournamentsDao {
  val id = integer("id").autoIncrement()
  val name = varchar("name", length = 255)
  val startDate = datetime("start_date")
  val endDate = datetime("end_date")
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  override val primaryKey = PrimaryKey(id, name = "PK_Tournaments_Id")

  override fun get(tournamentId: Int) =
    select {
      (id eq tournamentId)
    }.mapNotNull {
      it.mapRowToTournament()
    }.singleOrNull()

  override fun findAll(): List<Tournament> = selectAll().mapNotNull { it.mapRowToTournament() }

  override fun create(command: TournamentCreateCommand) =
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[name] = command.name
        it[startDate] = command.startDate.toLocalDateTime()
        it[endDate] = command.endDate.toLocalDateTime()
        it[createdAt] = now
        it[updatedAt] = now
      })[id]
    }

  override fun edit(command: TournamentUpdateCommand) =
    update({ id eq command.id }) { tournament ->
      command.name?.let { tournament[name] = it }
      command.startDate?.let { tournament[startDate] = it.toLocalDateTime() }
      command.endDate?.let { tournament[endDate] = it.toLocalDateTime() }
      tournament[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }

  override fun delete(tournamentId: Int) = deleteWhere { (id eq tournamentId) } > 0
}
