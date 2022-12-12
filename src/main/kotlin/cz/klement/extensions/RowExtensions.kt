package cz.klement.extensions

import cz.klement.model.dto.*
import cz.klement.tables.*
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.mapRowToTournament() =
  Tournament(
    id = this[Tournaments.id],
    name = this[Tournaments.name],
    startDate = this[Tournaments.startDate].toZonedInstant(),
    endDate = this[Tournaments.endDate].toZonedInstant(),
    createdAt = this[Tournaments.createdAt].toZonedInstant(),
    updatedAt = this[Tournaments.updatedAt].toZonedInstant()
  )

fun ResultRow.mapRowToTeam() =
  Team(
    id = this[Teams.id],
    name = this[Teams.name],
    createdAt = this[Teams.createdAt].toZonedInstant(),
    updatedAt = this[Teams.updatedAt].toZonedInstant()
  )

fun ResultRow.mapRowToGame() =
  Game(
    id = this[Games.id],
    tournament = this.mapRowToTournament(),
    homeTeam = this.mapRowToTeam(),
    awayTeam = this.mapRowToTeam(),
    startTime = this[Games.startTime].toZonedInstant(),
    homeScore = this[Games.homeScore],
    awayScore = this[Games.awayScore],
    createdAt = this[Games.createdAt].toZonedInstant(),
    updatedAt = this[Games.updatedAt].toZonedInstant()
  )

fun ResultRow.mapRowToUser() =
  User(
    id = this[Users.id],
    username = this[Users.username],
    password = this[Users.password],
    email = this[Users.email],
    firstName = this[Users.firstName],
    lastName = this[Users.lastName],
    createdAt = this[Users.createdAt].toZonedInstant(),
    updatedAt = this[Users.updatedAt].toZonedInstant()
  )

fun ResultRow.mapRowToPrediction() =
  Prediction(
    id = this[Predictions.id],
    user = this.mapRowToUser(),
    game = this.mapRowToGame(),
    homeScore = this[Predictions.homeScore],
    awayScore = this[Predictions.awayScore],
    points = this[Predictions.points],
    createdAt = this[Predictions.createdAt].toZonedInstant(),
    updatedAt = this[Predictions.updatedAt].toZonedInstant()
  )

fun ResultRow.mapRowToWinner() =
  Winner(
    id = this[Winners.id],
    user = this.mapRowToUser(),
    tournament = this.mapRowToTournament(),
    points = this[Winners.points],
    createdAt = this[Winners.createdAt].toZonedInstant(),
    updatedAt = this[Winners.updatedAt].toZonedInstant()
  )
