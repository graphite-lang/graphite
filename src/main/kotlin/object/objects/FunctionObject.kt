package org.graphite.`object`.objects

import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.ast.statements.BlockStatement
import org.graphite.evaluator.Environment
import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType

class FunctionObject(
    val name: IdentifierExpression,
    val parameters: MutableList<IdentifierExpression>,
    val body: BlockStatement,
    val environment: Environment
) :
    Object {
    override fun type(): ObjectType = ObjectType.FUNCTION
    override fun inspect(): String = """
fn $name (${parameters.joinToString(separator = ", ")}) {
    $body
}
    """.trimIndent()
}