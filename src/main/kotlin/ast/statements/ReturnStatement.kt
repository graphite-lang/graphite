package org.graphite.ast.statements

import org.graphite.ast.Expression
import org.graphite.ast.Statement
import org.graphite.token.Token

class ReturnStatement(override val token: Token, val value: Expression) :
    Statement {
    override fun toString(): String {
        return "${token.literal} $value"
    }

    override fun toStringTree(): String {
        return "ReturnStatement(token: $token, value: ${value.toStringTree()})"
    }
}
