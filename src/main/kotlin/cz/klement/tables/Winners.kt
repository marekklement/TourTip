package cz.klement.tables

import cz.klement.extensions.mapWinner
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.SearchCommand
import cz.klement.model.command.WinnerCreateCommand
import cz.klement.model.command.WinnerUpdateCommand
import cz.klement.model.structures.PageResult
import cz.klement.tables.api.TableBase
import cz.klement.tables.dao.WinnersDao
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

object Winners : TableBase("winners"), WinnersDao {
  val user = reference("user_id", Users)
    .nullable()
  val tournament = reference("tournament_id", Tournaments)
    .nullable()
  val points = integer("points")

  override fun get(winnerId: UUID) = transaction {
    Winner.find {
      Winners.id eq winnerId
    }.singleOrNull()
  }

  override fun findAll(): List<Winner> = transaction {
    Winner.all().toList()
  }

  override fun search(command: SearchCommand): PageResult<Winner> {
    val totalCount = selectAll().count()
    val content = this.let { if (command.where != null) it.select(command.where) else it.selectAll() }
      .let { if (command.n != null) it.limit(n = command.n, offset = command.offset) else it }
      .toList()
      .map {
        it.mapWinner()
      }
    return PageResult(
      content = content,
      pageNumber = command.offset,
      pageSize = command.n ?: content.size,
      totalCount = totalCount
    )
  }

  override fun create(command: WinnerCreateCommand) = transaction {
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[user] = command.userId
        it[tournament] = command.tournamentId
        it[points] = 0
        it[createdAt] = now
        it[updatedAt] = now
      })[Winners.id].value
    }
  }

  override fun edit(command: WinnerUpdateCommand) = transaction {
    update({ Winners.id eq command.id }) { winner ->
      command.userId?.let { winner[user] = it }
      command.tournamentId?.let { winner[tournament] = it }
      command.points?.let { winner[points] = it }
      winner[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }
  }

  override fun delete(winnerId: UUID) = transaction {
    deleteWhere { (id eq winnerId) } > 0
  }
}

class Winner(id: EntityID<UUID>) : UUIDEntity(id) {
  companion object : UUIDEntityClass<Winner>(Winners)

  val user by User optionalReferencedOn Winners.user
  val tournament by Tournament optionalReferencedOn Winners.tournament
  val points by Winners.points
  val createdAt by Winners.createdAt
  val updatedAt by Winners.updatedAt
}
