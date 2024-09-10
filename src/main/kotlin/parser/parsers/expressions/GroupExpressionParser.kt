package org.graphite.parser.parsers.expressions

import org.graphite.ast.Expression
import org.graphite.parser.Parser
import org.graphite.parser.Precedence
import org.graphite.parser.errors.SyntaxError
import org.graphite.parser.parsers.base.BaseExpressionParser
import org.graphite.parser.parsers.statements.ExpressionStatementParser
import org.graphite.token.TokenType

class GroupExpressionParser(private val parser: Parser) : BaseExpressionParser(parser = parser) {
    override fun parse(): Expression {
        if (parser.token.type != TokenType.LEFT_PARENTHESES) {
            throw SyntaxError(
                message = "Expected opening parentheses",
                position = parser.lexer.position,
            )
        }

        // eat (
        parser.eatToken()

        val expressionStatementParser = ExpressionStatementParser(parser = parser)
        val expression = expressionStatementParser.parseExpression(precedence = Precedence.LOWEST)

        if (parser.token.type != TokenType.RIGHT_PARENTHESES) {
            throw SyntaxError(
                message = "Expected closing parentheses after expression",
                position = parser.lexer.position,
            )
        }

        // eat )
        parser.eatToken()

        return expression
    }
}
