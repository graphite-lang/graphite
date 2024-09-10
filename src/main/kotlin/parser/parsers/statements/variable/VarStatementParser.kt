package org.graphite.parser.parsers.statements.variable

import org.graphite.ast.statements.variable.VarStatement
import org.graphite.parser.Parser
import org.graphite.parser.parsers.base.BaseStatementParser

class VarStatementParser(private val parser: Parser) : BaseStatementParser(parser = parser) {
    override fun parse(): VarStatement {
        val constStatementParser = ConstStatementParser(parser = parser)
        val constStatement = constStatementParser.parse()

        return VarStatement(
            token = constStatement.token,
            name = constStatement.name,
            value = constStatement.value,
        )
    }
}
