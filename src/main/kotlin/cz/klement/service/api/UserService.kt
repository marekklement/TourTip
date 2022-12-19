package cz.klement.service.api

import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserLoginCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.tables.User
import java.util.*

interface UserService {
  fun register(command: UserCreateCommand): User
  fun login(command: UserLoginCommand): String?
  fun editUserDetails(command: UserUpdateCommand): User
  fun changePassword(command: UserUpdateCommand): User
  fun delete(id: UUID)
  fun get(id: UUID): User
  fun findAll(): List<User>
}
