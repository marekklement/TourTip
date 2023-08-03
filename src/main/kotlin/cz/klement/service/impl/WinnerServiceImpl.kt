package cz.klement.service.impl

import cz.klement.model.command.WinnerCreateCommand
import cz.klement.model.command.WinnerUpdateCommand
import cz.klement.service.api.WinnerService
import cz.klement.tables.Winner
import cz.klement.tables.Winners
import io.ktor.server.plugins.*
import java.util.*

class WinnerServiceImpl : WinnerService {
  override fun create(command: WinnerCreateCommand): Winner = Winners.create(command).run(::get)

  override fun edit(command: WinnerUpdateCommand): Winner = Winners.edit(command) ?: throw NotFoundException("Winner with id ${command.id} cannot be found - no update processed!")

  override fun delete(id: UUID) {
    Winners.delete(id)
  }

  override fun get(id: UUID): Winner = Winners.get(id) ?: throw NotFoundException("Winner with id $id not found!")

  override fun findAll(): List<Winner> = Winners.findAll()
}
