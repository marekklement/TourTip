package cz.klement.tables

import cz.klement.extensions.mapTournament
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.SearchCommand
import cz.klement.model.command.TournamentCreateCommand
import cz.klement.model.command.TournamentUpdateCommand
import cz.klement.model.structures.PageResult
import cz.klement.tables.api.TableBase
import cz.klement.tables.dao.TournamentsDao
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

object Tournaments : TableBase("tournaments"), TournamentsDao {
  val name = varchar("name", length = 255)
  val startDate = datetime("start_date")
  val endDate = datetime("end_date")

  override fun get(tournamentId: UUID) = transaction {
    Tournament.find {
      Tournaments.id eq tournamentId
    }.singleOrNull()
  }

  override fun findAll(): List<Tournament> = transaction {
    Tournament.all().toList()
  }

  override fun create(command: TournamentCreateCommand) = transaction {
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[name] = command.name
        it[startDate] = command.startDate.toLocalDateTime()
        it[endDate] = command.endDate.toLocalDateTime()
        it[createdAt] = now
        it[updatedAt] = now
      })[Tournaments.id].value
    }
  }

  override fun edit(command: TournamentUpdateCommand) = transaction {
    update({ Tournaments.id eq command.id }) { tournament ->
      command.name?.let { tournament[name] = it }
      command.startDate?.let { tournament[startDate] = it.toLocalDateTime() }
      command.endDate?.let { tournament[endDate] = it.toLocalDateTime() }
      tournament[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }
  }

  override fun delete(tournamentId: UUID) = transaction {
    deleteWhere { (id eq tournamentId) } > 0
  }

  override fun search(command: SearchCommand): PageResult<Tournament> {
    val totalCount = selectAll().count()
    val content = this.let { if (command.where != null) it.select(command.where) else it.selectAll() }
      .let { if (command.n != null) it.limit(n = command.n, offset = command.offset) else it }
      .toList()
      .map {
        it.mapTournament()
      }
    return PageResult(
      content = content,
      pageNumber = command.offset,
      pageSize = command.n ?: content.size,
      totalCount = totalCount
    )
  }

  fun getSearch(command: SearchCommand) = getSearch(command) { this }

  private fun getSearch(command: SearchCommand, queryMutation: Query.() -> Query) = transaction {
    Tournaments.let { if (command.where != null) it.select(command.where) else it.selectAll() }
      .queryMutation()
      .let { if (command.n != null) it.limit(n = command.n, offset = command.offset) else it }
      .toList()
      .map {
        it.mapTournament()
      }
  }

}

class Tournament(id: EntityID<UUID>) : UUIDEntity(id) {
  companion object : UUIDEntityClass<Tournament>(Tournaments)

  var name by Tournaments.name
  var startDate by Tournaments.startDate
  var endDate by Tournaments.endDate
  var createdAt by Tournaments.createdAt
  var updatedAt by Tournaments.updatedAt
}
