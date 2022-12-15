package cz.klement.tables

import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.TeamCreateCommand
import cz.klement.model.command.TeamUpdateCommand
import cz.klement.tables.api.TableBase
import cz.klement.tables.dao.TeamsDao
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.util.*

object Teams : TableBase("teams"), TeamsDao {
  val name = varchar("name", length = 255)

  override fun get(teamId: UUID) = transaction {
    Team.find {
      Teams.id eq teamId
    }.singleOrNull()
  }


  override fun findAll(): List<Team> = transaction {
    Team.all().toList()
  }

  override fun create(command: TeamCreateCommand) = transaction {
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[name] = command.name
        it[createdAt] = now
        it[updatedAt] = now
      })[Teams.id].value
    }
  }


  override fun edit(command: TeamUpdateCommand) = transaction {
    update({ Teams.id eq command.id }) { team ->
      command.name?.let { team[name] = it }
      team[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }
  }


  override fun delete(teamId: UUID) = transaction {
    deleteWhere { (id eq teamId) } > 0
  }
}

class Team(id: EntityID<UUID>): UUIDEntity(id) {
  companion object : UUIDEntityClass<Team>(Teams)

  var name by Teams.name
  var createdAt by Teams.createdAt
  var updatedAt by Teams.updatedAt
}
