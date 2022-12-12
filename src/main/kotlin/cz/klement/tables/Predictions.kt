package cz.klement.tables

import cz.klement.dao.PredictionsDao
import cz.klement.extensions.mapRowToPrediction
import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.PredictionCreateCommand
import cz.klement.model.command.PredictionUpdateCommand
import cz.klement.model.dto.Prediction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Instant

object Predictions : Table(), PredictionsDao {
  val id = integer("id").autoIncrement()
  val userId = integer("user_id")
    .references(Users.id)
    .nullable()
  val gameId = integer("game_id")
    .references(Games.id)
    .nullable()
  val homeScore = integer("home_score")
  val awayScore = integer("away_score")
  val points = integer("points").default(0)
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  override val primaryKey = PrimaryKey(id, name = "PK_Predictions_Id")

  override fun get(predictionId: Int) =
    select {
      (id eq predictionId)
    }.mapNotNull {
      it.mapRowToPrediction()
    }.singleOrNull()

  override fun findAll(): List<Prediction> = selectAll().mapNotNull { it.mapRowToPrediction() }

  override fun create(command: PredictionCreateCommand) =
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[userId] = command.userId
        it[gameId] = command.gameId
        it[homeScore] = command.homeScore
        it[awayScore] = command.awayScore
        it[points] = 0
        it[createdAt] = now
        it[updatedAt] = now
      })[id]
    }

  override fun edit(command: PredictionUpdateCommand) =
    update({ id eq command.id }) { prediction ->
      command.userId?.let { prediction[userId] = it }
      command.gameId?.let { prediction[gameId] = it }
      command.homeScore?.let { prediction[homeScore] = it }
      command.awayScore?.let { prediction[awayScore] = it }
      command.points?.let { prediction[points] = it }
      prediction[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }

  override fun delete(predictionId: Int) = deleteWhere { (id eq predictionId) } > 0
}


