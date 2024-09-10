package org.graphite.parser.parsers.statements

import org.graphite.ast.Expression
import org.graphite.ast.statements.ExpressionStatement
import org.graphite.parser.Parser
import org.graphite.parser.Precedence
import org.graphite.parser.errors.SyntaxError
import org.graphite.parser.parsers.base.BaseStatementParser

class ExpressionStatementParser(private val parser: Parser) : BaseStatementParser(parser = parser) {
    override fun parse(): ExpressionStatement {
        val firstToken = parser.token

        val expression =
            parseExpression(precedence = Precedence.LOWEST)

        return ExpressionStatement(token = firstToken, expression = expression)
    }

    fun parseExpression(precedence: Precedence): Expression {
        val expressionParserClass =
            parser.prefixParseFns[parser.token.type]
                ?: throw SyntaxError(
                    message = "Unknown prefix ${parser.token.type}",
                    position = parser.lexer.position,
                )

        val expressionParser =
            expressionParserClass.constructors.first().call(parser)
        var left =
            expressionParser.parse()

        val infixParserClass = parser.infixParseFns[parser.token.type] ?: return left
        while (Precedence.precedenceFor(
                tokenType = parser.token.type,
            ) > precedence
        ) {
            val infixParser =
                infixParserClass.constructors.first().call(
                    parser,
                    left,
                )

            left = infixParser.parse()
        }

        return left
    }
}
