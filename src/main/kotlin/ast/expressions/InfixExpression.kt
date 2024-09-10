package org.graphite.ast.expressions

import org.graphite.ast.Expression
import org.graphite.token.Token

class InfixExpression(override val token: Token, val left: Expression, val operator: String, val right: Expression) :
    Expression {
    fun expressionNode() {}

    override fun toString(): String {
        return "($left $operator $right)"
    }

    override fun toStringTree(): String {
        return "InfixExpression(token: $token, value: left: ${left.toStringTree()}, operator: '$operator', right: ${right.toStringTree()})"
    }
}
