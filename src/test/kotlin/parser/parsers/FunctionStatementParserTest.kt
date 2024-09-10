package parser.parsers

import org.graphite.ast.statements.ExpressionStatement
import org.graphite.ast.statements.FunctionStatement
import org.graphite.lexer.Lexer
import org.graphite.parser.Parser
import org.junit.jupiter.api.Test
import parser.ParserTestHelper

class FunctionStatementParserTest {
    private val parserTestHelper = ParserTestHelper()

    @Test
    fun `Test function statement parser`() {
        listOf(
            listOf("fn add(x, y) { x + y }", "add", listOf("x", "y"), "x + y"),
        ).forEach { (input, name, parameters, body) ->
            val lexer = Lexer(input = input.toString())
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is FunctionStatement) {
                assert(statement.name != name) {
                    "Statement name is not `$name`! got `${statement.name}`"
                }

                val parameters = parameters as List<String>
                assert(statement.parameters.size == parameters.size) {
                    "Statement parameters size is not `${parameters.size}`! got `${statement.parameters.size}`"
                }

                for ((index, parameter) in parameters.withIndex()) {
                    assert(statement.parameters[index].token.literal == parameter) {
                        "Statement parameter is not `$parameter`! got `${statement.parameters[index]}`"
                    }
                }

                parserTestHelper.testExpression(
                    expression = (statement.body.statements[0] as ExpressionStatement).expression,
                    expected = body.toString(),
                )

                println(statement.toString())
                println(statement.toStringTree())
            } else {
                error("Statement is not `FunctionStatement`! got `${statement.javaClass}`")
            }
        }
    }
}
