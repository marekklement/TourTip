package cz.klement.service.impl

import cz.klement.model.command.PredictionCreateCommand
import cz.klement.model.command.PredictionUpdateCommand
import cz.klement.service.api.PredictionService
import cz.klement.tables.Prediction
import cz.klement.tables.Predictions
import io.ktor.server.plugins.*
import java.util.*

class PredictionServiceImpl : PredictionService {
  override fun create(command: PredictionCreateCommand): Prediction = Predictions.create(command).run(::get)

  override fun edit(command: PredictionUpdateCommand): Prediction = Predictions.edit(command) ?: throw NotFoundException("Prediction with id ${command.id} cannot be found - no update processed!")

  override fun delete(id: UUID) {
    Predictions.delete(id)
  }

  override fun get(id: UUID): Prediction =
    Predictions.get(id) ?: throw NotFoundException("Prediction with id $id not found!")

  override fun findAll(): List<Prediction> = Predictions.findAll()
}
