package org.graphite.parser.errors

import org.graphite.lexer.Position

abstract class ParserError(override val message: String, private val position: Position) :
    Error() {
    override fun toString(): String {
        return "${ParserError::class.java.name}: $message at ${position.line}:${position.column}"
    }
}
