package org.graphite.ast.expressions

import org.graphite.ast.Expression
import org.graphite.token.Token

class CallExpression(
    override val token: Token,
    val name: IdentifierExpression,
    val arguments: MutableList<Expression>,
) :
    Expression {
    fun expressionNode() {}

    override fun toString(): String {
        return "$name(${
            arguments.map { argument ->
                argument.toString()
            }.joinToString(separator = ", ")
        })"
    }

    override fun toStringTree(): String {
        return "CallExpression(token: $token, name: $name, parameters: [${
            arguments.map { argument ->
                argument.toStringTree()
            }.joinToString(separator = ", ")
        }])"
    }
}
