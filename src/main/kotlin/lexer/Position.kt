package org.graphite.lexer

data class Position(var line: Int, var column: Int) {
    override fun toString(): String {
        return "Position(line: $line, column: $column)"
    }
}
