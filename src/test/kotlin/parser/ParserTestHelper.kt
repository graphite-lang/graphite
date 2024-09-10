package parser

import org.graphite.ast.Expression
import org.graphite.ast.expressions.BooleanLiteralExpression
import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.ast.expressions.InfixExpression
import org.graphite.ast.expressions.IntegerExpression
import org.graphite.ast.expressions.StringLiteralExpression

class ParserTestHelper {
    fun testExpression(
        expression: Expression?,
        expected: String,
    ) {
        when (expression) {
            is IdentifierExpression -> {
                testIdentifierExpression(expression = expression, expected = expected)
            }

            is IntegerExpression -> {
                testIntegerExpression(expression = expression, expected = expected.toInt())
            }

            is BooleanLiteralExpression -> {
                testBooleanLiteralExpression(expression = expression, expected = expected.toBoolean())
            }

            is StringLiteralExpression -> {
                testStringLiteralExpression(expression = expression, expected = expected.toString())
            }
        }
    }

    fun testInfixExpression(
        expression: Expression?,
        left: String,
        operator: String,
        right: String,
    ) {
        if (expression is InfixExpression) {
            assert(expression.token.literal == operator) {
                "Expression token literal is not `$operator`! got `${expression.token.literal}`"
            }

            assert(expression.operator == operator) {
                "Expression operator is not `$operator`! got `${expression.token.literal}`"
            }

            testExpression(expression = expression.left, expected = left)
            testExpression(expression = expression.right, expected = right)
        } else {
            error("Expression is null or its not `InfixExpression`! got `$expression`")
        }
    }

    fun testIdentifierExpression(
        expression: Expression?,
        expected: String,
    ) {
        if (expression is IdentifierExpression) {
            assert(expression.token.literal == expected) {
                "Expression token literal is not `$expected`! got `${expression.token.literal}`"
            }

            assert(expression.value == expected) {
                "Expression value is not `$expected`! got `${expression.value}`"
            }
        } else {
            error("Expression is null or its not `IdentifierExpression`! got `$expression`")
        }
    }

    fun testIntegerExpression(
        expression: Expression?,
        expected: Int,
    ) {
        if (expression is IntegerExpression) {
            assert(expression.token.literal == expected.toString()) {
                "Expression token literal is not `$expected`! got `${expression.token.literal}`"
            }

            assert(expression.value == expected) {
                "Expression value is not `$expected`! got `${expression.value}`"
            }
        } else {
            error("Expression is null or its not `IntegerExpression`! got `$expression`")
        }
    }

    fun testBooleanLiteralExpression(
        expression: Expression?,
        expected: Boolean,
    ) {
        if (expression is BooleanLiteralExpression) {
            assert(expression.token.literal == expected.toString()) {
                "Expression token literal is not `$expected`! got `${expression.token.literal}`"
            }

            assert(expression.value == expected) {
                "Expression value is not `$expected`! got `${expression.value}`"
            }
        } else {
            error("Expression is null or its not `BooleanLiteralExpression`! got `$expression`")
        }
    }

    fun testStringLiteralExpression(
        expression: Expression?,
        expected: String,
    ) {
        if (expression is StringLiteralExpression) {
            assert(expression.token.literal == expected.toString()) {
                "Expression token literal is not `$expected`! got `${expression.token.literal}`"
            }

            assert(expression.value == expected) {
                "Expression value is not `$expected`! got `${expression.value}`"
            }
        } else {
            error("Expression is null or its not `StringLiteralExpression`! got `$expression`")
        }
    }
}