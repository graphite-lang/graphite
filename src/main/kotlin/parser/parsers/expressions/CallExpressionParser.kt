package org.graphite.parser.parsers.expressions

import org.graphite.ast.Expression
import org.graphite.ast.expressions.CallExpression
import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.parser.Parser
import org.graphite.parser.Precedence
import org.graphite.parser.errors.SyntaxError
import org.graphite.parser.parsers.base.BaseExpressionParser
import org.graphite.parser.parsers.statements.ExpressionStatementParser
import org.graphite.token.TokenType

class CallExpressionParser(private val parser: Parser, private val left: IdentifierExpression) :
    BaseExpressionParser(parser = parser) {
    override fun parse(): CallExpression {
        if (parser.token.type != TokenType.LEFT_PARENTHESES) {
            throw SyntaxError(
                message = "Expected left parentheses after identifier",
                position = parser.lexer.position,
            )
        }

        // eat (
        parser.eatToken()

        val arguments = mutableListOf<Expression>()
        val expressionStatementParser = ExpressionStatementParser(parser = parser)
        while (parser.token.type != TokenType.RIGHT_PARENTHESES) {
            arguments.add(expressionStatementParser.parseExpression(precedence = Precedence.LOWEST))

//            if (parser.token.type != TokenType.COMMA && parser.token.type != TokenType.RIGHT_PARENTHESES) {
//                throw SyntaxError(message = "Arguments should be separated by commas", position = parser.lexer.position)
//            }

            if (parser.token.type == TokenType.COMMA) {
                // eat comma
                parser.eatToken()
            }
        }

        // eat )
        parser.eatToken()

        return CallExpression(
            token = left.token,
            name = left,
            arguments = arguments,
        )
    }
}
