package org.graphite.parser.errors

import org.graphite.lexer.Position

class InternalError(override val message: String, private val position: Position) :
    ParserError(message = message, position = position) {
    override fun toString(): String {
        return "${InternalError::class.java.name}: $message at ${position.line}:${position.column}"
    }
}
