package cz.klement.service.impl

import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.service.api.UserService
import cz.klement.tables.User
import cz.klement.tables.Users
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserServiceImpl : UserService {

  override fun register(command: UserCreateCommand) = Users.create(command).let(::get)

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
