package parser.parsers

import org.graphite.ast.statements.variable.ConstStatement
import org.graphite.ast.statements.variable.VarStatement
import org.graphite.ast.statements.variable.VariableStatement
import org.graphite.lexer.Lexer
import org.graphite.parser.Parser
import org.junit.jupiter.api.Test
import parser.ParserTestHelper

class VariableStatementParserTest {
    private val parserTestHelper = ParserTestHelper()

    @Test
    fun `Test variable statement parser`() {
        listOf(
            listOf("const x = 5", "const", "x", "5"),
            listOf("const y = true", "const", "y", "true"),
            listOf("var x = 5", "var", "x", "5"),
            listOf("var y = true", "var", "y", "true"),
        ).forEach { (input, literal, identifier, value) ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            if ((literal == "const" && program.statements[0] is ConstStatement) || (literal == "var" && program.statements[0] is VarStatement)) {
                val statement = program.statements[0] as VariableStatement

                assert(statement.token.literal == literal) {
                    "Token literal is not `$literal`! got `${statement.token.literal}`"
                }

                parserTestHelper.testExpression(expression = statement.name, expected = identifier)
                parserTestHelper.testExpression(expression = statement.value, expected = value)

                println(statement.toString())
                println(statement.toStringTree())
            } else {
                error(
                    "Statement is not `${if (literal == "const") "ConstStatement" else "VarStatement"}`! got `${program.statements[0].javaClass}`",
                )
            }
        }
    }
}
