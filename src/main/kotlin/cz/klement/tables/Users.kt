package cz.klement.tables

import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.tables.api.TableBase
import cz.klement.tables.dao.UsersDao
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

object Users : TableBase("users"), UsersDao {
  val username = varchar("username", length = 255)
  val password = varchar("password", length = 255)
  val email = varchar("email", length = 255)
  val firstName = varchar("first_name", length = 255)
  val lastName = varchar("last_name", length = 255)

  override fun get(userId: UUID) = transaction {
    User.find {
      Users.id eq userId
    }.singleOrNull()
  }

  override fun findAll(): List<User> = transaction {
    User.all().toList()
  }

  override fun create(command: UserCreateCommand) = transaction {
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[username] = command.username
        it[password] = command.password
        it[email] = command.email
        it[firstName] = command.firstName
        it[lastName] = command.lastName
        it[createdAt] = now
        it[updatedAt] = now
      })[Users.id].value
    }
  }


  override fun edit(command: UserUpdateCommand) = transaction {
    update({ Users.id eq command.id }) { user ->
      command.username?.let { user[username] = it }
      command.email?.let { user[email] = it }
      command.firstName?.let { user[firstName] = it }
      command.lastName?.let { user[lastName] = it }
      user[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }
  }


  override fun delete(userId: UUID) = transaction {
    deleteWhere { (id eq userId) } > 0
  }
}

class User(id: EntityID<UUID>): UUIDEntity(id) {
  companion object : UUIDEntityClass<User>(Users)

  val username by Users.username
  val password by Users.password
  val email by Users.email
  val firstName by Users.firstName
  val lastName by Users.lastName
  val createdAt by Users.createdAt
  val updatedAt by Users.updatedAt
}
