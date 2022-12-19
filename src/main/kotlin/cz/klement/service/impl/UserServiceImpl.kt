package cz.klement.service.impl

import cz.klement.auth.getJWTToken
import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserLoginCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.service.api.UserService
import cz.klement.tables.User
import cz.klement.tables.Users
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.util.*

class UserServiceImpl : UserService {

  override fun register(command: UserCreateCommand) =
    Users.getByUsername(command.username).run {
      if (this != null) throw IllegalArgumentException("Username already exist!")
      Users.create(command)
    }.let(::get)

  override fun login(command: UserLoginCommand) =
    Users.getByUsername(command.username)?.run {
      if (BCrypt.checkpw(command.password, password)) getJWTToken(config = command.config, user = this)
      else null
    }

  override fun editUserDetails(command: UserUpdateCommand) = transaction {
    Users.edit(command) ?: throw NotFoundException("Edit of a user with id ${command.id} was not possible!")
  }


  override fun changePassword(command: UserUpdateCommand) = transaction {
    Users.edit(command) ?: throw NotFoundException("Change password of a user with id ${command.id} was not possible!")
  }


  override fun delete(id: UUID) {
    transaction {
      Users.delete(id)
    }
  }

  override fun get(id: UUID) = Users.get(id) ?: throw NotFoundException("User with id $id not found!")

  override fun findAll(): List<User> = Users.findAll()
}
