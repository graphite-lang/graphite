package org.graphite.parser.parsers.statements

import org.graphite.ast.statements.ReturnStatement
import org.graphite.parser.Parser
import org.graphite.parser.Precedence
import org.graphite.parser.errors.SyntaxError
import org.graphite.parser.parsers.base.BaseStatementParser
import org.graphite.token.TokenType

class ReturnStatementParser(private val parser: Parser) : BaseStatementParser(parser = parser) {
    override fun parse(): ReturnStatement {
        val returnToken = parser.token

        // eat return
        parser.eatToken()

        if (parser.token.type == TokenType.EOL) {
            throw SyntaxError(
                message = "Expected expression after return",
                position = parser.lexer.position,
            )
        }

        val expressionStatementParser = ExpressionStatementParser(parser = parser)
        val value = expressionStatementParser.parseExpression(precedence = Precedence.LOWEST)

        return ReturnStatement(token = returnToken, value = value)
    }
}
