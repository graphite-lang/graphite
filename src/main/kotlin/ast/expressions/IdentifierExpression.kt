package org.graphite.ast.expressions

import org.graphite.ast.Expression
import org.graphite.token.Token

class IdentifierExpression(override val token: Token, val value: String) : Expression {
    fun expressionNode() {}

    override fun toString(): String {
        return value
    }

    override fun toStringTree(): String {
        return "IdentifierExpression(token: $token, value: '$value')"
    }
}
