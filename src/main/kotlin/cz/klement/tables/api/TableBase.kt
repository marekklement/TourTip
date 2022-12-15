package cz.klement.tables.api

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

open class TableBase(name: String) : UUIDTable(name) {
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")
}
