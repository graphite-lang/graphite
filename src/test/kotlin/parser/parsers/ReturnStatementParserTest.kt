package parser.parsers

import org.graphite.ast.statements.ReturnStatement
import org.graphite.lexer.Lexer
import org.graphite.parser.Parser
import org.junit.jupiter.api.Test
import parser.ParserTestHelper

class ReturnStatementParserTest {
    private val parserTestHelper = ParserTestHelper()

    @Test
    fun `Test return statement`() {
        listOf(
            listOf("return 123", "123"),
            listOf("return asd", "asd"),
            listOf("return true", "true")
        ).forEach { (input, value) ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            for (statement in program.statements) {
                if (statement is ReturnStatement) {
                    assert(statement.token.literal == "return") {
                        "Token literal is not `return`! got `${statement.token.literal}`"
                    }

                    parserTestHelper.testExpression(expression = statement.value, expected = value)

                    println(statement.toString())
                    println(statement.toStringTree())
                } else {
                    error("Statement is not `ReturnStatement`! got `${statement.javaClass}`")
                }
            }
        }
    }
}
