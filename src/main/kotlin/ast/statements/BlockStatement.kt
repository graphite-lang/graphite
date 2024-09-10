package org.graphite.ast.statements

import org.graphite.ast.Statement
import org.graphite.token.Token

class BlockStatement(override val token: Token, val statements: MutableList<Statement>) :
    Statement {
    override fun toString(): String {
        return statements.joinToString(separator = "\n")
    }

    override fun toStringTree(): String {
        return "BlockStatement(token: $token, statements: [${
            statements.map { statement ->
                listOf(statement.toStringTree(), ",").joinToString("")
            }
        }])"
    }
}
