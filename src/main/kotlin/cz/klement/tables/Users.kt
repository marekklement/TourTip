package cz.klement.tables

import cz.klement.dao.UsersDao
import cz.klement.extensions.mapRowToUser
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.model.dto.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

object Users : Table(), UsersDao {
  val id = integer("id").autoIncrement()
  val username = varchar("username", length = 255)
  val password = varchar("password", length = 255)
  val email = varchar("email", length = 255)
  val firstName = varchar("first_name", length = 255)
  val lastName = varchar("last_name", length = 255)
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  override val primaryKey = PrimaryKey(id, name = "PK_Users_Id")

  override fun get(userId: Int) =
    select {
      (id eq userId)
    }.mapNotNull {
      it.mapRowToUser()
    }.singleOrNull()

  override fun findAll(): List<User> = transaction {
    selectAll().mapNotNull { it.mapRowToUser() }
  }

  override fun create(command: UserCreateCommand) =
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[username] = command.username
        it[password] = command.password
        it[email] = command.email
        it[firstName] = command.firstName
        it[lastName] = command.lastName
        it[createdAt] = now
        it[updatedAt] = now
      })[id]
    }

  override fun edit(command: UserUpdateCommand) =
    update({ id eq command.id }) { user ->
      command.username?.let { user[username] = it }
      command.email?.let { user[email] = it }
      command.firstName?.let { user[firstName] = it }
      command.lastName?.let { user[lastName] = it }
      user[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }

  override fun delete(userId: Int) = deleteWhere { (id eq userId) } > 0
}
