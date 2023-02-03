package cz.klement.service.api

import cz.klement.model.command.TournamentCreateCommand
import cz.klement.model.command.TournamentUpdateCommand
import cz.klement.tables.Tournament
import java.util.*

interface TournamentService {
  fun create(command: TournamentCreateCommand): Tournament
  fun edit(command: TournamentUpdateCommand): Tournament
  fun delete(id: UUID)

  //  fun search(command: TournamentSearchCommand): PageResult<Tournament>
  fun get(id: UUID): Tournament
}
