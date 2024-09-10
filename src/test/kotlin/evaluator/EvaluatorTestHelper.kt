package evaluator

import org.graphite.evaluator.Environment
import org.graphite.evaluator.Evaluator
import org.graphite.lexer.Lexer
import org.graphite.`object`.Object
import org.graphite.parser.Parser

class EvaluatorTestHelper {
    fun testEvaluator(input: String): Object? {
        val lexer = Lexer(input = input)
        val parser = Parser(lexer = lexer)

        val program = parser.parseProgram()

        assert(program.statements.isNotEmpty()) {
            "Program does not have enough statements!"
        }

        val evaluator = Evaluator()
        val environment = Environment()
        return evaluator.evaluate(node = program, environment = environment)
    }
}