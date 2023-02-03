package cz.klement.tables.dao

import cz.klement.model.command.PredictionCreateCommand
import cz.klement.model.command.PredictionUpdateCommand
import cz.klement.model.command.SearchCommand
import cz.klement.model.structures.PageResult
import cz.klement.tables.Prediction
import java.util.*

interface PredictionsDao {
  fun get(predictionId: UUID): Prediction?
  fun findAll(): List<Prediction>
  fun create(command: PredictionCreateCommand): UUID
  fun edit(command: PredictionUpdateCommand): Prediction?
  fun delete(predictionId: UUID): Boolean
  fun search(command: SearchCommand): PageResult<Prediction>
}
