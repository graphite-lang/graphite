package lexer

import org.graphite.lexer.Lexer
import org.graphite.token.TokenType
import org.junit.jupiter.api.Test

class LexerTest {
    private val lexerTestHelper = LexerTestHelper()

    @Test
    fun `Test everything`() {
        val input =
            """
fn add(a, b) {
    if (a > 100) {
        return 101
    } else {
        return a + b
    }
}

fn main() {
    var thousand = 999
    thousand = "1000"

    const result = add(5, thousand)

    if (1 == 1) {
        return 1
    } else if (1 != 0) {
        return 0
    }
}
            """.trimIndent()

        Lexer(input = input)

        listOf(
            Pair(TokenType.FN, "fn"),
            Pair(TokenType.IDENTIFIER, "add"),
            Pair(TokenType.LEFT_PARENTHESES, "("),
            Pair(TokenType.IDENTIFIER, "a"),
            Pair(TokenType.COMMA, ","),
            Pair(TokenType.IDENTIFIER, "b"),
            Pair(TokenType.RIGHT_PARENTHESES, ")"),
            Pair(TokenType.LEFT_BRACE, "{"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.IF, "if"),
            Pair(TokenType.LEFT_PARENTHESES, "("),
            Pair(TokenType.IDENTIFIER, "a"),
            Pair(TokenType.GREATER_THAN, ">"),
            Pair(TokenType.INT, "100"),
            Pair(TokenType.RIGHT_PARENTHESES, ")"),
            Pair(TokenType.LEFT_BRACE, "{"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RETURN, "return"),
            Pair(TokenType.INT, "101"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RIGHT_BRACE, "}"),
            Pair(TokenType.ELSE, "else"),
            Pair(TokenType.LEFT_BRACE, "{"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RETURN, "return"),
            Pair(TokenType.IDENTIFIER, "a"),
            Pair(TokenType.PLUS, "+"),
            Pair(TokenType.IDENTIFIER, "b"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RIGHT_BRACE, "}"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RIGHT_BRACE, "}"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.FN, "fn"),
            Pair(TokenType.IDENTIFIER, "main"),
            Pair(TokenType.LEFT_PARENTHESES, "("),
            Pair(TokenType.RIGHT_PARENTHESES, ")"),
            Pair(TokenType.LEFT_BRACE, "{"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.VAR, "var"),
            Pair(TokenType.IDENTIFIER, "thousand"),
            Pair(TokenType.ASSIGN, "="),
            Pair(TokenType.INT, "999"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.IDENTIFIER, "thousand"),
            Pair(TokenType.ASSIGN, "="),
            Pair(TokenType.INT, "1000"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.CONST, "const"),
            Pair(TokenType.IDENTIFIER, "result"),
            Pair(TokenType.ASSIGN, "="),
            Pair(TokenType.IDENTIFIER, "add"),
            Pair(TokenType.LEFT_PARENTHESES, "("),
            Pair(TokenType.INT, "5"),
            Pair(TokenType.COMMA, ","),
            Pair(TokenType.IDENTIFIER, "thousand"),
            Pair(TokenType.RIGHT_PARENTHESES, ")"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.IF, "if"),
            Pair(TokenType.LEFT_PARENTHESES, "("),
            Pair(TokenType.INT, "1"),
            Pair(TokenType.EQUAL, "=="),
            Pair(TokenType.INT, "1"),
            Pair(TokenType.RIGHT_PARENTHESES, ")"),
            Pair(TokenType.LEFT_BRACE, "{"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RETURN, "return"),
            Pair(TokenType.INT, "1"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RIGHT_BRACE, "}"),
            Pair(TokenType.ELSE, "else"),
            Pair(TokenType.IF, "if"),
            Pair(TokenType.LEFT_PARENTHESES, "("),
            Pair(TokenType.INT, "1"),
            Pair(TokenType.NOT_EQUAL, "!="),
            Pair(TokenType.INT, "0"),
            Pair(TokenType.RIGHT_PARENTHESES, ")"),
            Pair(TokenType.LEFT_BRACE, "{"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RETURN, "return"),
            Pair(TokenType.INT, "0"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RIGHT_BRACE, "}"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.RIGHT_BRACE, "}"),
            Pair(TokenType.EOL, ""),
            Pair(TokenType.EOF, ""),
        ).forEachIndexed { index, (expectedTokenType, expectedLiteral) ->
            lexerTestHelper.testLexer(
                expectedTokenType = expectedTokenType,
                expectedLiteral = expectedLiteral
            )
        }
    }
}
