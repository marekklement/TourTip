package cz.klement.tables

import cz.klement.dao.TeamsDao
import cz.klement.extensions.mapRowToTeam
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.TeamCreateCommand
import cz.klement.model.command.TeamUpdateCommand
import cz.klement.model.dto.Team
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Instant

object Teams : Table(), TeamsDao {
  val id = integer("id").autoIncrement()
  val name = varchar("name", length = 255)
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  override val primaryKey = PrimaryKey(id, name = "PK_Teams_Id")

  override fun get(teamId: Int) =
    select {
      (id eq teamId)
    }.mapNotNull {
      it.mapRowToTeam()
    }.singleOrNull()

  override fun findAll(): List<Team> = selectAll().mapNotNull { it.mapRowToTeam() }

  override fun create(command: TeamCreateCommand) =
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[name] = command.name
        it[createdAt] = now
        it[updatedAt] = now
      })[id]
    }

  override fun edit(command: TeamUpdateCommand) =
    update({ id eq command.id }) { team ->
      command.name?.let { team[name] = it }
      team[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }

  override fun delete(teamId: Int) = deleteWhere { (id eq teamId) } > 0
}
