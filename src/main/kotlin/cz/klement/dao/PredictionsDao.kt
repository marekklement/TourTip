package cz.klement.dao

import cz.klement.model.command.PredictionCreateCommand
import cz.klement.model.command.PredictionUpdateCommand
import cz.klement.model.dto.Prediction

interface PredictionsDao {
  fun get(predictionId: Int): Prediction?
  fun findAll(): List<Prediction>
  fun create(command: PredictionCreateCommand): Int
  fun edit(command: PredictionUpdateCommand): Prediction?
  fun delete(predictionId: Int): Boolean
}
