package org.graphite.parser.parsers.base

import org.graphite.ast.Statement
import org.graphite.parser.Parser

abstract class BaseStatementParser(private val parser: Parser) {
    abstract fun parse(): Statement
}
