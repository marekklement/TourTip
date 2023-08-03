package cz.klement.service.api

import cz.klement.model.command.WinnerCreateCommand
import cz.klement.model.command.WinnerUpdateCommand
import cz.klement.tables.Winner
import java.util.UUID

interface WinnerService {
  fun create(command: WinnerCreateCommand): Winner
  fun edit(command: WinnerUpdateCommand): Winner
  fun delete(id: UUID)
  fun get(id: UUID): Winner
  fun findAll(): List<Winner>
}
