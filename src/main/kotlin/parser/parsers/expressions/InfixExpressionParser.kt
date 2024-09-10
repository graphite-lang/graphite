package org.graphite.parser.parsers.expressions

import org.graphite.ast.Expression
import org.graphite.ast.expressions.InfixExpression
import org.graphite.parser.Parser
import org.graphite.parser.Precedence
import org.graphite.parser.parsers.base.BaseExpressionParser
import org.graphite.parser.parsers.statements.ExpressionStatementParser

class InfixExpressionParser(private val parser: Parser, private val left: Expression) :
    BaseExpressionParser(parser = parser) {
    override fun parse(): InfixExpression {
        val operatorToken = parser.token

        // eat operator
        parser.eatToken()

        val expressionStatementParser = ExpressionStatementParser(parser = parser)
        val right =
            expressionStatementParser.parseExpression(
                precedence =
                Precedence.precedenceFor(tokenType = operatorToken.type),
            )

        return InfixExpression(token = operatorToken, left = left, operator = operatorToken.literal, right = right)
    }
}
