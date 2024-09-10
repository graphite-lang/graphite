package org.graphite.evaluator

import org.graphite.ast.Expression
import org.graphite.ast.Node
import org.graphite.ast.Program
import org.graphite.ast.Statement
import org.graphite.ast.expressions.BooleanLiteralExpression
import org.graphite.ast.expressions.CallExpression
import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.ast.expressions.IfExpression
import org.graphite.ast.expressions.InfixExpression
import org.graphite.ast.expressions.IntegerExpression
import org.graphite.ast.expressions.PrefixExpression
import org.graphite.ast.expressions.StringLiteralExpression
import org.graphite.ast.statements.BlockStatement
import org.graphite.ast.statements.ExpressionStatement
import org.graphite.ast.statements.FunctionStatement
import org.graphite.ast.statements.ReturnStatement
import org.graphite.ast.statements.variable.ConstStatement
import org.graphite.ast.statements.variable.VarStatement
import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType
import org.graphite.`object`.objects.BooleanObject
import org.graphite.`object`.objects.BuiltinObject
import org.graphite.`object`.objects.ErrorObject
import org.graphite.`object`.objects.FunctionObject
import org.graphite.`object`.objects.IntObject
import org.graphite.`object`.objects.NullObject
import org.graphite.`object`.objects.ReturnObject
import org.graphite.`object`.objects.StringObject
import org.graphite.token.TokenType

object ObjectConstants {
    val TRUE = BooleanObject(value = true)
    val FALSE = BooleanObject(value = false)
    val NULL = NullObject()
}

class Evaluator() {
    private fun booleanToBooleanObject(value: Boolean): BooleanObject {
        return if (value) ObjectConstants.TRUE else ObjectConstants.FALSE
    }

    private fun isError(obj: Object?): Boolean {
        return obj is ErrorObject
    }

    fun evaluate(node: Node, environment: Environment): Object {
        return when (node) {
            is Program -> {
                evaluateProgram(statements = node.statements, environment = environment)
            }

            is BlockStatement -> {
                evaluateBlockStatement(statements = node.statements, environment = environment)
            }

            is IfExpression -> {
                evaluateIfExpression(node = node, environment = environment)
            }

            is ReturnStatement -> {
                val value = evaluate(node = node.value, environment = environment)
                if (isError(obj = value)) return value

                ReturnObject(value = value)
            }

            is ConstStatement, is VarStatement -> {
                val value = evaluate(node = node.value, environment = environment)
                if (isError(obj = value)) return value

                environment.set(name = node.name.value, value = value)

                ObjectConstants.NULL
            }

            is FunctionStatement -> {
                environment.set(
                    name = node.name.value, value = FunctionObject(
                        name = node.name,
                        parameters = node.parameters,
                        body = node.body,
                        environment = environment
                    )
                )

                ObjectConstants.NULL
            }

            is CallExpression -> {
                val function = evaluate(node = node.name, environment = environment)
                if (isError(function)) {
                    return function
                }

                val arguments = evaluateExpressions(expressions = node.arguments, environment = environment)
                if (isError(obj = arguments.getOrNull(index = 0))) {
                    return arguments[0]
                }

                return applyFunction(function, arguments)
            }

            is ExpressionStatement -> {
                evaluate(node = node.expression, environment = environment)
            }

            is IntegerExpression -> {
                IntObject(value = node.value)
            }

            is BooleanLiteralExpression -> {
                booleanToBooleanObject(value = node.value)
            }

            is StringLiteralExpression -> {
                StringObject(value = node.value)
            }

            is PrefixExpression -> {
                val right = evaluate(node = node.right, environment = environment)
                if (isError(obj = right)) return right

                evaluatePrefixExpression(
                    prefix = node.token.type, right = right
                )
            }

            is InfixExpression -> {
                val left = evaluate(node = node.left, environment = environment)
                if (isError(obj = left)) return left

                val right = evaluate(node = node.right, environment = environment)
                if (isError(obj = right)) return right

                evaluateInfixExpression(
                    operator = node.token.type,
                    left = left,
                    right = right
                )
            }

            is IdentifierExpression -> {
                evaluateIdentifier(node = node, environment = environment)
            }

            else -> ErrorObject("not implemented")
        }
    }

    fun evaluateProgram(statements: List<Statement>, environment: Environment): Object {
        return statements.map { statement ->
            val result = evaluate(node = statement, environment = environment)

            when (result) {
                is ReturnObject -> return result.value
                is ErrorObject -> return result

                else -> result
            }
        }.last()
    }

    fun evaluateBlockStatement(statements: List<Statement>, environment: Environment): Object {
        val blockStatementEnvironment = Environment(outer = environment)

        return statements.map { statement ->
            val result = evaluate(node = statement, environment = blockStatementEnvironment)

            when (result.type()) {
                ObjectType.RETURN -> return result
                ObjectType.ERROR -> return result

                else -> result
            }
        }.last()
    }

    fun evaluateIdentifier(node: IdentifierExpression, environment: Environment): Object {
        val value = environment.get(name = node.value)
        if (value != null) {
            return value
        }

        val builtin = builtins.get(key = node.value)
        if (builtin != null) {
            return BuiltinObject(fn = builtin)
        }

        return ErrorObject("identifier not found: ${node.value}")
    }

    fun evaluateIfExpression(node: IfExpression, environment: Environment): Object {
        val condition = evaluate(node = node.condition, environment = environment)
        if (isError(obj = condition)) return condition

        return if (isTruthy(obj = condition)) {
            evaluate(node = node.consequence, environment = environment)
        } else if (node.alternative != null) {
            evaluate(node = node.alternative, environment = environment)
        } else {
            ObjectConstants.NULL
        }
    }

    fun applyFunction(function: Object, arguments: List<Object>): Object {
        return when (function) {
            is FunctionObject -> {
                val functionEnvironment = Environment(outer = function.environment)
                function.parameters.forEachIndexed { index, parameter ->
                    functionEnvironment.set(parameter.value, arguments[index])
                }

                return evaluate(node = function.body, environment = functionEnvironment)
            }

            is BuiltinObject -> {
                return function.fn(arguments)
            }

            else -> ErrorObject(message = "not a function: ${function.type()}")
        }
    }

    fun evaluateExpressions(expressions: List<Expression>, environment: Environment): List<Object> {
        return expressions.map { expression ->
            val evaluated = evaluate(node = expression, environment = environment)
            if (isError(evaluated)) {
                return listOf(evaluated)
            }

            evaluated
        }
    }

    fun isTruthy(obj: Object): Boolean {
        return when (obj) {
            ObjectConstants.TRUE -> true
            ObjectConstants.FALSE -> false

            else -> false
        }
    }

    fun evaluatePrefixExpression(prefix: TokenType, right: Object): Object {
        return when (prefix) {
            TokenType.BANG -> {
                if (right.type() != ObjectType.BOOLEAN) {
                    ErrorObject(message = "type mismatch: !${right.type()}")
                }

                return when (right) {
                    ObjectConstants.TRUE -> {
                        ObjectConstants.FALSE
                    }

                    ObjectConstants.FALSE -> {
                        ObjectConstants.TRUE
                    }

                    else -> ObjectConstants.NULL
                }
            }

            TokenType.MINUS -> {
                if (right.type() != ObjectType.INT) {
                    ErrorObject(message = "type mismatch: -${right.type()}")
                }

                if (right is IntObject) {
                    IntObject(value = -right.value)
                } else {
                    ObjectConstants.NULL
                }
            }

            else -> ErrorObject(message = "unknown prefix $prefix for ${right.type()}")
        }
    }

    fun evaluateInfixExpression(operator: TokenType, left: Object, right: Object): Object {
        return when {
            left.type() != right.type() -> ErrorObject(message = "type mismatch: ${left.type()}, $operator, ${right.type()}")

            left.type() == ObjectType.INT -> {
                evaluateIntInfixExpression(
                    operator = operator,
                    left = left as IntObject,
                    right = right as IntObject
                )
            }

            left.type() == ObjectType.STRING -> {
                evaluateStringInfixExpression(
                    operator = operator,
                    left = left as StringObject,
                    right = right as StringObject
                )
            }

            operator == TokenType.EQUAL -> {
                booleanToBooleanObject(value = left == right)
            }

            operator == TokenType.NOT_EQUAL -> {
                booleanToBooleanObject(value = left != right)
            }

            else -> ErrorObject(message = "unknown operator: ${left.type()}, $operator, ${right.type()}")
        }
    }

    fun evaluateIntInfixExpression(operator: TokenType, left: IntObject, right: IntObject): Object {
        return when (operator) {
            TokenType.PLUS -> {
                return IntObject(value = left.value + right.value)
            }

            TokenType.MINUS -> {
                return IntObject(value = left.value - right.value)
            }

            TokenType.ASTERISK -> {
                return IntObject(value = left.value * right.value)
            }

            TokenType.SLASH -> {
                return IntObject(value = left.value / right.value)
            }

            TokenType.EQUAL -> {
                return booleanToBooleanObject(value = left.value == right.value)
            }

            TokenType.NOT_EQUAL -> {
                return booleanToBooleanObject(value = left.value != right.value)
            }

            TokenType.GREATER_THAN -> {
                return booleanToBooleanObject(value = left.value > right.value)
            }

            TokenType.LESS_THAN -> {
                return booleanToBooleanObject(value = left.value < right.value)
            }

            else -> ErrorObject("unknown operator: ${left.type()}, $operator, ${right.type()}")
        }
    }

    fun evaluateStringInfixExpression(operator: TokenType, left: StringObject, right: StringObject): Object {
        return when (operator) {
            TokenType.PLUS -> {
                return StringObject(value = left.value + right.value)
            }

            else -> ErrorObject("unknown operator: ${left.type()}, $operator, ${right.type()}")
        }
    }
}