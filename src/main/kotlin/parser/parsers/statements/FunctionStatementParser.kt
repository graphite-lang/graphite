package org.graphite.parser.parsers.statements

import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.ast.statements.FunctionStatement
import org.graphite.parser.Parser
import org.graphite.parser.Precedence
import org.graphite.parser.errors.SyntaxError
import org.graphite.parser.parsers.base.BaseStatementParser
import org.graphite.token.TokenType

class FunctionStatementParser(private val parser: Parser) : BaseStatementParser(parser = parser) {
    override fun parse(): FunctionStatement {
        val fnToken = parser.token

        // eat fn
        parser.eatToken()

        if (parser.token.type != TokenType.IDENTIFIER) {
            throw SyntaxError(
                message = "Expected name identifier after fn",
                position = parser.lexer.position,
            )
        }

        val expressionStatementParser = ExpressionStatementParser(parser = parser)
        val name = expressionStatementParser.parseExpression(precedence = Precedence.CALL) as IdentifierExpression

        if (parser.token.type != TokenType.LEFT_PARENTHESES) {
            throw SyntaxError(
                message = "Expected opening parentheses after fn name",
                position = parser.lexer.position,
            )
        }

        val parameters = parseParameters()

        if (parser.token.type != TokenType.LEFT_BRACE) {
            throw SyntaxError(
                message = "Expected opening brace after parameters",
                position = parser.lexer.position,
            )
        }

        val blockStatementParser = BlockStatementParser(parser = parser)
        val body = blockStatementParser.parse()

        return FunctionStatement(token = fnToken, name = name, parameters = parameters, body = body)
    }

    private fun parseParameters(): MutableList<IdentifierExpression> {
        // eat (
        parser.eatToken()

        val expressionStatementParser = ExpressionStatementParser(parser = parser)
        val parameters = mutableListOf<IdentifierExpression>()
        while (parser.token.type != TokenType.RIGHT_PARENTHESES) {
            val expression = expressionStatementParser.parseExpression(precedence = Precedence.LOWEST)
            if (expression !is IdentifierExpression) {
                throw SyntaxError(message = "Expected identifier as param", position = parser.lexer.position)
            }

            parameters.add(expression)
            
            if (parser.token.type != TokenType.COMMA && parser.token.type != TokenType.RIGHT_PARENTHESES) {
                throw SyntaxError(
                    message = "Parameters should be separated by commas",
                    position = parser.lexer.position
                )
            }

            if (parser.token.type == TokenType.COMMA) {
                // eat comma
                parser.eatToken()
            }
        }

        // eat )
        parser.eatToken()

        return parameters
    }
}
