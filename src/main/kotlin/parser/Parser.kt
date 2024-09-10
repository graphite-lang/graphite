package org.graphite.parser

import org.graphite.ast.Program
import org.graphite.ast.Statement
import org.graphite.lexer.Lexer
import org.graphite.parser.parsers.expressions.BooleanLiteralExpressionParser
import org.graphite.parser.parsers.expressions.CallExpressionParser
import org.graphite.parser.parsers.expressions.GroupExpressionParser
import org.graphite.parser.parsers.expressions.IdentifierExpressionParser
import org.graphite.parser.parsers.expressions.IfExpressionParser
import org.graphite.parser.parsers.expressions.InfixExpressionParser
import org.graphite.parser.parsers.expressions.IntegerLiteralExpressionParser
import org.graphite.parser.parsers.expressions.PrefixExpressionParser
import org.graphite.parser.parsers.expressions.StringLiteralExpressionParser
import org.graphite.parser.parsers.statements.StatementParser
import org.graphite.token.Token
import org.graphite.token.TokenType

class Parser(val lexer: Lexer) {
    private var position: Int = 0

    lateinit var nextToken: Token
    lateinit var token: Token

    var prefixParseFns =
        mapOf(
            TokenType.IDENTIFIER to IdentifierExpressionParser::class,
            TokenType.INT to IntegerLiteralExpressionParser::class,
            TokenType.STRING to StringLiteralExpressionParser::class,
            TokenType.BANG to PrefixExpressionParser::class,
            TokenType.PLUS to PrefixExpressionParser::class,
            TokenType.MINUS to PrefixExpressionParser::class,
            TokenType.TRUE to BooleanLiteralExpressionParser::class,
            TokenType.FALSE to BooleanLiteralExpressionParser::class,
            TokenType.LEFT_PARENTHESES to GroupExpressionParser::class,
            TokenType.IF to IfExpressionParser::class
        )
    var infixParseFns =
        mapOf(
            TokenType.PLUS to InfixExpressionParser::class,
            TokenType.MINUS to InfixExpressionParser::class,
            TokenType.SLASH to InfixExpressionParser::class,
            TokenType.ASTERISK to InfixExpressionParser::class,
            TokenType.EQUAL to InfixExpressionParser::class,
            TokenType.NOT_EQUAL to InfixExpressionParser::class,
            TokenType.LESS_THAN to InfixExpressionParser::class,
            TokenType.GREATER_THAN to InfixExpressionParser::class,
            TokenType.LEFT_PARENTHESES to CallExpressionParser::class,
        )

    private fun currentToken(): Token {
        return lexer.tokens.getOrNull(index = position) ?: lexer.tokens.last()
    }

    fun eatToken() {
        lexer.nextToken()
        token = currentToken()

        position += 1

        lexer.nextToken()
        nextToken = currentToken()
    }

    fun debug(name: String) {
        println("--- $name ---")

        println(token)
        println(nextToken)
    }

    fun parseProgram(): Program {
        val statementParser = StatementParser(parser = this)

        val statements = mutableListOf<Statement>()
        while (token.type != TokenType.EOF) {
            statementParser.parse()?.let { statement -> statements.add(element = statement) }
        }

        return Program(statements = statements)
    }

    init {
        eatToken()
    }
}
