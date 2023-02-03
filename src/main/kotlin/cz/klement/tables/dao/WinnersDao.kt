package cz.klement.tables.dao

import cz.klement.model.command.SearchCommand
import cz.klement.model.command.WinnerCreateCommand
import cz.klement.model.command.WinnerUpdateCommand
import cz.klement.model.structures.PageResult
import cz.klement.tables.Winner
import java.util.*

interface WinnersDao {
  fun get(winnerId: UUID): Winner?
  fun findAll(): List<Winner>
  fun create(command: WinnerCreateCommand): UUID
  fun edit(command: WinnerUpdateCommand): Winner?
  fun delete(winnerId: UUID): Boolean
  fun search(command: SearchCommand): PageResult<Winner>
}
