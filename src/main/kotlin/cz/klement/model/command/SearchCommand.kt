package cz.klement.model.command

import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder

data class SearchCommand(
  val n: Int?,
  val offset: Long,
  val where: (SqlExpressionBuilder.() -> Op<Boolean>)? = null
)
