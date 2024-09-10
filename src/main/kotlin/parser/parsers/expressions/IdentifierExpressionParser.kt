package org.graphite.parser.parsers.expressions

import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.parser.Parser
import org.graphite.parser.parsers.base.BaseExpressionParser

class IdentifierExpressionParser(private val parser: Parser) : BaseExpressionParser(parser = parser) {
    override fun parse(): IdentifierExpression {
        val identifierToken = parser.token

        // eat identifier
        parser.eatToken()

        return IdentifierExpression(token = identifierToken, value = identifierToken.literal)
    }
}
