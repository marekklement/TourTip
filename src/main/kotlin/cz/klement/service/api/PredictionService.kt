package cz.klement.service.api

import cz.klement.model.command.PredictionCreateCommand
import cz.klement.model.command.PredictionUpdateCommand
import cz.klement.tables.Prediction
import java.util.UUID

interface PredictionService {
  fun create(command: PredictionCreateCommand): Prediction
  fun edit(command: PredictionUpdateCommand): Prediction
  fun delete(id: UUID)
  fun get(id: UUID): Prediction
  fun findAll(): List<Prediction>
}
