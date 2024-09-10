package org.graphite.parser.parsers.statements.variable

import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.ast.statements.variable.ConstStatement
import org.graphite.parser.Parser
import org.graphite.parser.Precedence
import org.graphite.parser.errors.SyntaxError
import org.graphite.parser.parsers.base.BaseStatementParser
import org.graphite.parser.parsers.statements.ExpressionStatementParser
import org.graphite.token.TokenType

class ConstStatementParser(private val parser: Parser) : BaseStatementParser(parser = parser) {
    override fun parse(): ConstStatement {
        val constToken = parser.token

        // eat const
        parser.eatToken()

        if (parser.token.type != TokenType.IDENTIFIER) {
            throw SyntaxError(
                message = "Expected an identifier after const",
                position = parser.lexer.position,
            )
        }

        val name = IdentifierExpression(token = parser.token, value = parser.token.literal)

        // eat identifier
        parser.eatToken()

        if (parser.token.type != TokenType.ASSIGN) {
            throw SyntaxError(
                message = "Expected assign after identifier",
                position = parser.lexer.position,
            )
        }

        // eat assign
        parser.eatToken()

        if (parser.token.type == TokenType.EOL) {
            throw SyntaxError(
                message = "Expected an expression after assignment",
                position = parser.lexer.position,
            )
        }

        val expressionStatementParser = ExpressionStatementParser(parser = parser)
        val value = expressionStatementParser.parseExpression(precedence = Precedence.LOWEST)

        return ConstStatement(token = constToken, name = name, value = value)
    }
}
