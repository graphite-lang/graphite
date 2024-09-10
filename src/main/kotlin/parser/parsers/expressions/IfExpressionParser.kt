package org.graphite.parser.parsers.expressions

import org.graphite.ast.expressions.IfExpression
import org.graphite.ast.statements.BlockStatement
import org.graphite.parser.Parser
import org.graphite.parser.errors.SyntaxError
import org.graphite.parser.parsers.base.BaseExpressionParser
import org.graphite.parser.parsers.statements.BlockStatementParser
import org.graphite.token.TokenType

class IfExpressionParser(private val parser: Parser) :
    BaseExpressionParser(parser = parser) {
    override fun parse(): IfExpression {
        val ifToken = parser.token

        // eat if
        parser.eatToken()

        if (parser.token.type != TokenType.LEFT_PARENTHESES) {
            throw SyntaxError(
                message = "Expected opening parentheses after if",
                position = parser.lexer.position,
            )
        }

        val groupExpressionParser = GroupExpressionParser(parser = parser)
        val condition = groupExpressionParser.parse()

        if (parser.token.type != TokenType.LEFT_BRACE) {
            throw SyntaxError(
                message = "Expected opening brace after condition",
                position = parser.lexer.position,
            )
        }

        val blockStatementParser = BlockStatementParser(parser = parser)
        val consequence = blockStatementParser.parse()

        var alternative: BlockStatement? = null
        if (parser.token.type == TokenType.ELSE) {
            // eat else
            parser.eatToken()

            if (parser.token.type == TokenType.LEFT_BRACE) {
                alternative = blockStatementParser.parse()
            } else {
                throw SyntaxError(
                    message = "Expected opening brace after else",
                    position = parser.lexer.position,
                )
            }
        }

        return IfExpression(
            token = ifToken,
            condition = condition,
            consequence = consequence,
            alternative = alternative,
        )
    }
}
