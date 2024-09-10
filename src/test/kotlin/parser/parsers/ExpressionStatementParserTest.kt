package parser.parsers

import org.graphite.ast.expressions.CallExpression
import org.graphite.ast.expressions.IfExpression
import org.graphite.ast.expressions.PrefixExpression
import org.graphite.ast.statements.ExpressionStatement
import org.graphite.lexer.Lexer
import org.graphite.parser.Parser
import org.junit.jupiter.api.Test
import parser.ParserTestHelper

class ExpressionStatementParserTest {
    private val parserTestHelper = ParserTestHelper()

    @Test
    fun `Test identifier expression`() {
        listOf("kotlin", "java", "my_variable").forEach { input ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is ExpressionStatement) {
                val expression = statement.expression

                parserTestHelper.testExpression(expression = expression, expected = input)

                println(statement.toString())
                println(statement.toStringTree())
            } else {
                error("Statement is not `ExpressionStatement`! got `${statement.javaClass}`")
            }
        }
    }

    @Test
    fun `Test integer expression`() {
        listOf("10", "5", "100").forEach { input ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is ExpressionStatement) {
                val expression = statement.expression

                parserTestHelper.testExpression(expression = expression, expected = input)

                println(statement.toString())
                println(statement.toStringTree())
            } else {
                error("Statement is not `ExpressionStatement`! got `${statement.javaClass}`")
            }
        }
    }

    @Test
    fun `Test boolean literal expression`() {
        listOf("true", "false").forEach { input ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is ExpressionStatement) {
                val expression = statement.expression

                parserTestHelper.testExpression(expression = expression, expected = input)

                println(statement.toString())
                println(statement.toStringTree())
            } else {
                error("Statement is not `ExpressionStatement`! got `${statement.javaClass}`")
            }
        }
    }

    @Test
    fun `Test string literal expression`() {
        listOf(listOf("\"hello\"", "hello")).forEach { (input, expected) ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is ExpressionStatement) {
                val expression = statement.expression

                parserTestHelper.testExpression(expression = expression, expected = expected)

                println(statement.toString())
                println(statement.toStringTree())
            } else {
                error("Statement is not `ExpressionStatement`! got `${statement.javaClass}`")
            }
        }
    }

    @Test
    fun `Test parsing prefix expressions`() {
        listOf(
            listOf("+15", "+", "15"),
            listOf("-15", "-", "15"),
            listOf("!false", "!", "false"),
            listOf("!true", "!", "true"),
        ).forEach { (input, prefix, value) ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is ExpressionStatement) {
                val expression = statement.expression

                if (expression is PrefixExpression) {
                    assert(expression.token.literal == prefix) {
                        "Expression token literal is not `$prefix`! got `${expression.token.literal}`"
                    }

                    assert(expression.prefix == prefix) {
                        "Expression prefix is not `$prefix`! got `${expression.token.literal}`"
                    }

                    parserTestHelper.testExpression(expression = expression.right, expected = value)

                    println(statement.toString())
                    println(statement.toStringTree())
                } else {
                    error("Expression is null or its not `PrefixExpression`!")
                }
            } else {
                error("Statement is not `ExpressionStatement`! got `${statement.javaClass}`")
            }
        }
    }

    @Test
    fun `Test parsing infix expressions`() {
        listOf(
            listOf("5 + 5", "5", "+", "5"),
            listOf("5 - 5", "5", "-", "5"),
            listOf("5 / 5", "5", "/", "5"),
            listOf("5 * 5", "5", "*", "5"),
            listOf("5 < 5", "5", "<", "5"),
            listOf("5 > 5", "5", ">", "5"),
            listOf("5 == 5", "5", "==", "5"),
            listOf("5 != 5", "5", "!=", "5"),
            listOf("true == true", "true", "==", "true"),
            listOf("true != false", "true", "!=", "false"),
            listOf("false == false", "false", "==", "false"),
        ).forEach { (input, left, operator, right) ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is ExpressionStatement) {
                parserTestHelper.testInfixExpression(
                    expression = statement.expression,
                    left = left,
                    operator = operator,
                    right = right
                )

                println(statement.toString())
                println(statement.toStringTree())
            } else {
                error("Statement is not `ExpressionStatement`! got `${statement.javaClass}`")
            }
        }
    }

    @Test
    fun `Test operator precedence`() {
        listOf(
            Pair("-a * b", "((-a) * b)"),
            Pair("!-a", "(!(-a))"),
            Pair("a + b + c", "((a + b) + c)"),
            Pair("a + b - c", "((a + b) - c)"),
            Pair("a * b * c", "((a * b) * c)"),
            Pair("a * b / c", "((a * b) / c)"),
            Pair("a + b / c", "(a + (b / c))"),
            Pair("a + b * c + d / e - f", "(((a + (b * c)) + (d / e)) - f)"),
            Pair(
                """
                3 + 4
                -5 * 5
                """.trimIndent(),
                """
                (3 + 4)
                ((-5) * 5)
                """.trimIndent(),
            ),
            Pair("5 > 4 == 3 < 4", "((5 > 4) == (3 < 4))"),
            Pair("5 < 4 != 3 > 4", "((5 < 4) != (3 > 4))"),
            Pair("3 + 4 * 5 == 3 * 1 + 4 * 5", "((3 + (4 * 5)) == ((3 * 1) + (4 * 5)))"),
            Pair("3 + 4 * 5 == 3 * 1 + 4 * 5", "((3 + (4 * 5)) == ((3 * 1) + (4 * 5)))"),
            Pair("true", "true"),
            Pair("false", "false"),
            Pair("3 > 5 == false", "((3 > 5) == false)"),
            Pair("3 < 5 == true", "((3 < 5) == true)"),
            Pair("1 + (2 + 3) + 4", "((1 + (2 + 3)) + 4)"),
            Pair("(5 + 5) * 2", "((5 + 5) * 2)"),
            Pair("2 / (5 + 5)", "(2 / (5 + 5))"),
            Pair("-(5 + 5)", "(-(5 + 5))"),
            Pair("!(true == true)", "(!(true == true))"),
        ).forEach { (input, result) ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            assert(result == program.toString()) {
                "Expected result is not equal to the program! expected `$result`, got `$program`"
            }

            println(program.toString())
            println(program.toStringTree())
        }
    }

    @Test
    fun `Test if expression`() {
        listOf(
            listOf("if (x > y) { y }", "x", ">", "y", "y", ""),
            listOf("if (x > y) { y } else { x }", "x", ">", "y", "y", "x"),
        ).forEach { (input, left, operator, right, consequence, alternative) ->
            val lexer = Lexer(input = input)
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is ExpressionStatement) {
                val expression = statement.expression
                if (expression is IfExpression) {
                    parserTestHelper.testInfixExpression(
                        expression = expression.condition,
                        left = left,
                        operator = operator,
                        right = right,
                    )

                    assert(expression.consequence.statements.isNotEmpty()) {
                        "Consequence does not have enough statements!"
                    }

                    val consequenceStatement = expression.consequence.statements[0]
                    if (consequenceStatement is ExpressionStatement) {
                        parserTestHelper.testExpression(
                            expression = consequenceStatement.expression,
                            expected = consequence
                        )
                    } else {
                        error("Consequence statement is not `ExpressionStatement`! got `${consequenceStatement.javaClass}`")
                    }

                    if (expression.alternative == null) {
                        assert(alternative.isBlank()) {
                            "Expected alternative statement but got null!"
                        }
                    } else {
                        assert(alternative.isNotBlank()) {
                            "Expected alternative statement to be null got `${expression.alternative}`"
                        }

                        assert(expression.alternative.statements.isNotEmpty()) {
                            "Alternative does not have enough statements!"
                        }

                        val alternativeStatement = expression.alternative.statements[0]
                        if (alternativeStatement is ExpressionStatement) {
                            parserTestHelper.testExpression(
                                expression = alternativeStatement.expression,
                                expected = alternative,
                            )
                        } else {
                            error("Alternative statement is not `ExpressionStatement`! got `${alternativeStatement.javaClass}`")
                        }
                    }

                    println(statement.toString())
                    println(statement.toStringTree())
                } else {
                    error("Expression is null or its not `IfExpression`! got `$expression`")
                }
            } else {
                error("Statement is not `ExpressionStatement`! got `${statement.javaClass}`")
            }
        }
    }

    @Test
    fun `Test call expression`() {
        listOf(
            listOf("add(1, 2)", "add", listOf("1", "2")),
            listOf("add(1, 2, 3)", "add", listOf("1", "2", "3")),
            listOf("add(1 + 2, 3 * 4, 5 + 6)", "add", listOf("(1 + 2)", "(3 * 4)", "(5 + 6)")),
        ).forEach { (input, name, arguments) ->
            val lexer = Lexer(input = input.toString())
            val parser = Parser(lexer = lexer)

            val program = parser.parseProgram()

            println(program.toString())
            println(input)

            assert(program.statements.isNotEmpty()) {
                "Program does not have enough statements!"
            }

            val statement = program.statements[0]
            if (statement is ExpressionStatement) {
                val expression = statement.expression
                if (expression is CallExpression) {
                    parserTestHelper.testIdentifierExpression(
                        expression = expression.name,
                        expected = name.toString()
                    )

                    val arguments = (arguments as List<String>)
                    assert(expression.arguments.size == arguments.size) {
                        "Expected arguments size is not equal to the program! expected `${arguments.size}`, got `${expression.arguments.size}`"
                    }

                    for ((index, argument) in arguments.withIndex()) {
                        parserTestHelper.testExpression(
                            expression = expression.arguments[index],
                            expected = argument
                        )
                    }

                    println(statement.toString())
                    println(statement.toStringTree())
                } else {
                    error("Expression is null or its not `CallExpression`! got `$expression`")
                }
            } else {
                error("Statement is not `ExpressionStatement`! got `${statement.javaClass}`")
            }
        }
    }
}

operator fun <T> List<T>.component6() = this[5]
operator fun <T> List<T>.component7() = this[6]
operator fun <T> List<T>.component8() = this[7]
operator fun <T> List<T>.component9() = this[8]
operator fun <T> List<T>.component10() = this[9]