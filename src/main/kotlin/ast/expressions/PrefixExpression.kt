package org.graphite.ast.expressions

import org.graphite.ast.Expression
import org.graphite.token.Token

class PrefixExpression(override val token: Token, val prefix: String, val right: Expression) : Expression {
    fun expressionNode() {}

    override fun toString(): String {
        return "(${prefix}$right)"
    }

    override fun toStringTree(): String {
        return "PrefixExpression(token: $token, prefix: '$prefix', right: ${right.toStringTree()})"
    }
}
