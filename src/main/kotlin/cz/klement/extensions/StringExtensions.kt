package cz.klement.extensions

import java.util.*

fun String.toUUID() = UUID.fromString(this)
