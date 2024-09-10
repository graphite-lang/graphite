package org.graphite.parser.errors

import org.graphite.lexer.Position

class SyntaxError(override val message: String, private val position: Position) :
    ParserError(message = message, position = position) {
    override fun toString(): String {
        return "${SyntaxError::class.java.name}: $message at ${position.line}:${position.column}"
    }
}
