package org.graphite.ast.statements

import org.graphite.ast.Expression
import org.graphite.ast.Statement
import org.graphite.token.Token

class ExpressionStatement(override val token: Token, val expression: Expression) :
    Statement {
    override fun toString(): String {
        return expression.toString()
    }

    override fun toStringTree(): String {
        return expression.toStringTree()
    }
}
