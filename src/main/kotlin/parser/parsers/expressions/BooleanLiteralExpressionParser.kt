package org.graphite.parser.parsers.expressions

import org.graphite.ast.expressions.BooleanLiteralExpression
import org.graphite.parser.Parser
import org.graphite.parser.parsers.base.BaseExpressionParser
import org.graphite.token.TokenType

class BooleanLiteralExpressionParser(private val parser: Parser) : BaseExpressionParser(parser = parser) {
    override fun parse(): BooleanLiteralExpression {
        val booleanToken = parser.token

        // eat boolean
        parser.eatToken()

        return BooleanLiteralExpression(token = booleanToken, value = booleanToken.type == TokenType.TRUE)
    }
}
