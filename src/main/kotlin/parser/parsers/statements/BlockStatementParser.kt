package org.graphite.parser.parsers.statements

import org.graphite.ast.Statement
import org.graphite.ast.statements.BlockStatement
import org.graphite.parser.Parser
import org.graphite.parser.errors.SyntaxError
import org.graphite.parser.parsers.base.BaseStatementParser
import org.graphite.token.TokenType

class BlockStatementParser(private val parser: Parser) : BaseStatementParser(parser = parser) {
    override fun parse(): BlockStatement {
        val leftBraceToken = parser.token

        if (parser.token.type != TokenType.LEFT_BRACE) {
            throw SyntaxError(
                message = "Expected opening brace",
                position = parser.lexer.position,
            )
        }

        // eat {
        parser.eatToken()

        val statementParser = StatementParser(parser = parser)

        val statements = mutableListOf<Statement>()
        while (parser.token.type != TokenType.RIGHT_BRACE) {
            statementParser.parse()?.let { statement ->
                statements.add(element = statement)
            }
        }

        if (parser.token.type != TokenType.RIGHT_BRACE) {
            throw SyntaxError(
                message = "Expected closing brace",
                position = parser.lexer.position,
            )
        }

        // eat }
        parser.eatToken()

        return BlockStatement(token = leftBraceToken, statements = statements)
    }
}
