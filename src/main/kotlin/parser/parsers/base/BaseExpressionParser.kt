package org.graphite.parser.parsers.base

import org.graphite.ast.Expression
import org.graphite.parser.Parser

abstract class BaseExpressionParser(private val parser: Parser) {
    abstract fun parse(): Expression
}
