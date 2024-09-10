package org.graphite.parser.parsers.expressions

import org.graphite.ast.expressions.PrefixExpression
import org.graphite.parser.Parser
import org.graphite.parser.Precedence
import org.graphite.parser.parsers.base.BaseExpressionParser
import org.graphite.parser.parsers.statements.ExpressionStatementParser

class PrefixExpressionParser(private val parser: Parser) : BaseExpressionParser(parser = parser) {
    override fun parse(): PrefixExpression {
        val prefixToken = parser.token

        // eat prefix
        parser.eatToken()

        val expressionStatementParser = ExpressionStatementParser(parser = parser)
        val right = expressionStatementParser.parseExpression(precedence = Precedence.PREFIX)

        return PrefixExpression(token = prefixToken, prefix = prefixToken.literal, right = right)
    }
}
