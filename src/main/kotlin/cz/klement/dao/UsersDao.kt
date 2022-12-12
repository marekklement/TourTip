package cz.klement.dao

import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.model.dto.User

interface UsersDao {
  fun get(userId: Int): User?
  fun findAll(): List<User>
  fun create(command: UserCreateCommand): Int
  fun edit(command: UserUpdateCommand): User?
  fun delete(userId: Int): Boolean
}
