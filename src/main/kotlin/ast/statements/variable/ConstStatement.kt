package org.graphite.ast.statements.variable

import org.graphite.ast.Expression
import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.token.Token

class ConstStatement(
    override val token: Token,
    override val name: IdentifierExpression,
    override val value: Expression,
) :
    VariableStatement(token = token, name = name, value = value) {
    override fun toStringTree(): String {
        return "ConstStatement(token: $token, name: ${name.toStringTree()}, value: ${value.toStringTree()})"
    }
}
