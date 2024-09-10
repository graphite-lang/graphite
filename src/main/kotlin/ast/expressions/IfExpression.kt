package org.graphite.ast.expressions

import org.graphite.ast.Expression
import org.graphite.ast.statements.BlockStatement
import org.graphite.token.Token

class IfExpression(
    override val token: Token,
    val condition: Expression,
    val consequence: BlockStatement,
    val alternative: BlockStatement?,
) : Expression {
    fun expressionNode() {}

    override fun toString(): String {
        val alternative =
            if (alternative != null) {
                """
else {
    $alternative
}
                """.trimIndent()
            } else {
                ""
            }

        return """
${token.literal} ($condition) {
    $consequence
} $alternative
            """.trimIndent()
    }

    override fun toStringTree(): String {
        return "IfExpression(token: $token, condition: ${condition.toStringTree()}, consequence: ${consequence.toStringTree()}, alternative: ${alternative?.toStringTree()})"
    }
}
