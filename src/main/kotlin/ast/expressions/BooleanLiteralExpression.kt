package org.graphite.ast.expressions

import org.graphite.ast.Expression
import org.graphite.token.Token

class BooleanLiteralExpression(override val token: Token, val value: Boolean) : Expression {
    fun expressionNode() {}

    override fun toString(): String {
        return token.literal
    }

    override fun toStringTree(): String {
        return "BooleanLiteralExpression(token: $token, value: '$value')"
    }
}
