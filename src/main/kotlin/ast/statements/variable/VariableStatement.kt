package org.graphite.ast.statements.variable

import org.graphite.ast.Expression
import org.graphite.ast.Statement
import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.token.Token

open class VariableStatement(
    override val token: Token,
    open val name: IdentifierExpression,
    open val value: Expression,
) :
    Statement {
    override fun toString(): String {
        return "${token.literal} $name = $value"
    }

    override fun toStringTree(): String {
        return "VariableStatement(token: $token, name: ${name.toStringTree()}, value: ${value.toStringTree()})"
    }
}
