package cz.klement.extensions

import cz.klement.tables.*
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.mapTournament(): Tournament = Tournament.wrapRow(this)
fun ResultRow.mapGame(): Game = Game.wrapRow(this)
fun ResultRow.mapPrediction(): Prediction = Prediction.wrapRow(this)
fun ResultRow.mapTeam(): Team = Team.wrapRow(this)
fun ResultRow.mapUser(): User = User.wrapRow(this)
fun ResultRow.mapWinner(): Winner = Winner.wrapRow(this)
