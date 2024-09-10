package org.graphite.ast.expressions

import org.graphite.ast.Expression
import org.graphite.token.Token

class StringLiteralExpression(override val token: Token, val value: String) : Expression {
    fun expressionNode() {}

    override fun toString(): String {
        return token.literal
    }

    override fun toStringTree(): String {
        return "StringLiteralExpression(token: $token, value: '$value')"
    }
}
