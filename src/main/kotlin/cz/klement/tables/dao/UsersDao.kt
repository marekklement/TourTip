package cz.klement.tables.dao

import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.tables.User
import java.util.*

interface UsersDao {
  fun get(userId: UUID): User?
  fun getByUsername(searchUsername: String): User?
  fun findAll(): List<User>
  fun create(command: UserCreateCommand): UUID
  fun edit(command: UserUpdateCommand): User?
  fun delete(userId: UUID): Boolean
}
