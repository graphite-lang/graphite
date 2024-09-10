package parser.parsers

import org.graphite.ast.statements.BlockStatement
import org.graphite.lexer.Lexer
import org.graphite.parser.Parser
import org.junit.jupiter.api.Test

class BlockStatementParserTest {
    @Test
    fun `Test block statement parser`() {
        listOf("{ x + 5 }").forEach { input ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is BlockStatement) {
                println(statement.toString())
                println(statement.toStringTree())
            } else {
                error("Statement is not `BlockStatement`! got `${statement.javaClass}`")
            }
        }
    }
}
