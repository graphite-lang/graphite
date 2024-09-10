package org.graphite.ast

class Program(val statements: MutableList<Statement>) : Node {
    override fun toString(): String {
        return statements.joinToString(separator = "\n")
    }

    override fun toStringTree(): String {
        return "Program(statements: [${
            statements.joinToString(separator = ", ") { statement ->
                statement.toStringTree()
            }
        }])"
    }
}
