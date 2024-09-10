package org.graphite.parser.parsers.expressions

import org.graphite.ast.expressions.StringLiteralExpression
import org.graphite.parser.Parser
import org.graphite.parser.parsers.base.BaseExpressionParser

class StringLiteralExpressionParser(private val parser: Parser) : BaseExpressionParser(parser = parser) {
    override fun parse(): StringLiteralExpression {
        val stringToken = parser.token

        // eat string
        parser.eatToken()

        return StringLiteralExpression(token = stringToken, value = stringToken.literal)
    }
}
