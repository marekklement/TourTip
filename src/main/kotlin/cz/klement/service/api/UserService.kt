package cz.klement.service.api

import cz.klement.model.command.UserCreateCommand
import cz.klement.model.command.UserUpdateCommand
import cz.klement.model.dto.User

interface UserService {
  fun register(command: UserCreateCommand): User
  fun editUserDetails(command: UserUpdateCommand): User
  fun changePassword(command: UserUpdateCommand): User
  fun delete(id: Int)
  fun get(id: Int): User
  fun findAll(): List<User>
}
