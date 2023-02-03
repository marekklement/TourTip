package cz.klement.serialization

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.Instant

object InstantAdapter : TypeAdapter<Instant>() {

  override fun write(writer: JsonWriter, value: Instant?) {
    runCatching {
      writer.value(value.toString())
    }.onFailure {
      throw IllegalArgumentException(it.message)
    }
  }

  override fun read(reader: JsonReader): Instant {
    return kotlin.runCatching {
      Instant.parse(reader.nextString())
    }.getOrNull() ?: throw IllegalArgumentException("Cant read Instant!")
  }
}
