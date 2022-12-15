package cz.klement.tables

import cz.klement.extensions.toLocalDateTime
import cz.klement.model.command.PredictionCreateCommand
import cz.klement.model.command.PredictionUpdateCommand
import cz.klement.tables.api.TableBase
import cz.klement.tables.dao.PredictionsDao
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.util.*

object Predictions : TableBase("predictions"), PredictionsDao {
  val user = reference("user_id", Users)
    .nullable()
  val game = reference("game_id", Games)
    .nullable()
  val homeScore = integer("home_score")
  val awayScore = integer("away_score")
  val points = integer("points").default(0)

  override fun get(predictionId: UUID) = transaction {
    Prediction.find {
      Predictions.id eq predictionId
    }.singleOrNull()
  }


  override fun findAll(): List<Prediction> = transaction {
    Prediction.all().toList()
  }

  override fun create(command: PredictionCreateCommand) = transaction {
    Instant.now().toLocalDateTime().let { now ->
      (insert {
        it[user] = command.userId
        it[game] = command.gameId
        it[homeScore] = command.homeScore
        it[awayScore] = command.awayScore
        it[points] = 0
        it[createdAt] = now
        it[updatedAt] = now
      })[Predictions.id].value
    }
  }


  override fun edit(command: PredictionUpdateCommand) = transaction {
    update({ Predictions.id eq command.id }) { prediction ->
      command.userId?.let { prediction[user] = it }
      command.gameId?.let { prediction[game] = it }
      command.homeScore?.let { prediction[homeScore] = it }
      command.awayScore?.let { prediction[awayScore] = it }
      command.points?.let { prediction[points] = it }
      prediction[updatedAt] = Instant.now().toLocalDateTime()
    }.run {
      get(command.id)
    }
  }


  override fun delete(predictionId: UUID) = transaction {
    deleteWhere { (id eq predictionId) } > 0
  }
}

class Prediction(id: EntityID<UUID>): UUIDEntity(id) {
  companion object : UUIDEntityClass<Prediction>(Predictions)

  val user by User optionalReferencedOn Predictions.user
  val game by Game optionalReferencedOn Predictions.game
  val homeScore by Predictions.homeScore
  val awayScore by Predictions.awayScore
  val points by Predictions.points
  val createdAt by Predictions.createdAt
  val updatedAt by Predictions.updatedAt
}


