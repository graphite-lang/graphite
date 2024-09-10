package org.graphite.ast

interface Node {
    override fun toString(): String

    fun toStringTree(): String
}
