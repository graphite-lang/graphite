package org.graphite.parser.parsers.statements

import org.graphite.ast.Statement
import org.graphite.parser.Parser
import org.graphite.parser.parsers.statements.variable.ConstStatementParser
import org.graphite.parser.parsers.statements.variable.VarStatementParser
import org.graphite.token.TokenType

class StatementParser(private val parser: Parser) {
    fun parse(): Statement? {
        return when (parser.token.type) {
            TokenType.CONST -> {
                val constStatementParser = ConstStatementParser(parser = parser)
                constStatementParser.parse()
            }

            TokenType.VAR -> {
                val varStatementParser = VarStatementParser(parser = parser)
                varStatementParser.parse()
            }

            TokenType.FN -> {
                val functionStatementParser = FunctionStatementParser(parser = parser)
                functionStatementParser.parse()
            }

            TokenType.RETURN -> {
                val returnStatementParser = ReturnStatementParser(parser = parser)
                returnStatementParser.parse()
            }

            TokenType.LEFT_BRACE -> {
                val blockStatementParser = BlockStatementParser(parser = parser)
                blockStatementParser.parse()
            }

            TokenType.SEMICOLON -> {
                parser.eatToken()
                null
            }

            TokenType.EOL, TokenType.EOF -> {
                parser.eatToken()
                null
            }

            else -> {
                val expressionStatementParser = ExpressionStatementParser(parser = parser)
                expressionStatementParser.parse()
            }
        }
    }
}
