package cz.klement.dao

import cz.klement.model.command.*
import cz.klement.model.dto.Tournament

interface TournamentsDao {
  fun get(tournamentId: Int): Tournament?
  fun findAll(): List<Tournament>
  fun create(command: TournamentCreateCommand): Int
  fun edit(command: TournamentUpdateCommand): Tournament?
  fun delete(tournamentId: Int): Boolean
}
