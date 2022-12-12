package cz.klement.dao

import cz.klement.model.command.WinnerCreateCommand
import cz.klement.model.command.WinnerUpdateCommand
import cz.klement.model.dto.Winner

interface WinnersDao {
  fun get(winnerId: Int): Winner?
  fun findAll(): List<Winner>
  fun create(command: WinnerCreateCommand): Int
  fun edit(command: WinnerUpdateCommand): Winner?
  fun delete(winnerId: Int): Boolean
}
