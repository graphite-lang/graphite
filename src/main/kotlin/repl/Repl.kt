package org.graphite.repl

import org.graphite.evaluator.Environment
import org.graphite.evaluator.Evaluator
import org.graphite.lexer.Lexer
import org.graphite.parser.Parser

class Repl {
    fun run(buffer: String) {
        val lexer = Lexer(input = buffer.toString())
        val parser = Parser(lexer = lexer)

        val program = parser.parseProgram()

        val evaluator = Evaluator()
        val environment = Environment()

        evaluator.evaluate(node = program, environment = environment)
    }

    fun start() {
        println("Welcome to Graphite REPL!")

        val buffer = StringBuilder()

        while (true) {
            print(">> ")

            val input = readlnOrNull() ?: break
            buffer.append(input).append("\n")

            run(buffer = buffer.toString())
        }
    }
}
