package cz.klement.model.structures

data class PageResult<T>(
  val content: List<T>,
  val pageSize: Int,
  val pageNumber: Long,
  val totalCount: Long
)
