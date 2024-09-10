package org.graphite.parser.parsers.expressions

import org.graphite.ast.expressions.IntegerExpression
import org.graphite.parser.Parser
import org.graphite.parser.errors.InternalError
import org.graphite.parser.parsers.base.BaseExpressionParser

class IntegerLiteralExpressionParser(private val parser: Parser) : BaseExpressionParser(parser = parser) {
    override fun parse(): IntegerExpression {
        try {
            val integerToken = parser.token

            // eat integer
            parser.eatToken()

            return IntegerExpression(token = integerToken, value = integerToken.literal.toInt())
        } catch (e: Exception) {
            throw InternalError(
                message = "Couldn't parse ${parser.token.literal} as integer!",
                position = parser.lexer.position,
            )
        }
    }
}
