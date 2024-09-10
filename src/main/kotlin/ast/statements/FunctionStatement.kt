package org.graphite.ast.statements

import org.graphite.ast.Statement
import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.token.Token

class FunctionStatement(
    override val token: Token,
    val name: IdentifierExpression,
    val parameters: MutableList<IdentifierExpression>,
    val body: BlockStatement,
) :
    Statement {
    override fun toString(): String {
        return "${token.literal} $name(${
            parameters.map { parameter ->
                parameter.toString()
            }.joinToString(separator = ", ")
        }) { $body }"
    }

    override fun toStringTree(): String {
        return "FunctionStatement(token: $token, name: ${name.toStringTree()}, parameters: [${
            parameters.map { parameter ->
                parameter.toStringTree()
            }.joinToString(separator = ", ")
        }], body: ${body.toStringTree()})"
    }
}
