package cz.klement.extensions

import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun String.toUUID() = UUID.fromString(this)

fun String.hashPassword() = BCrypt.hashpw(this, BCrypt.gensalt())
