package org.graphite.`object`.objects

import org.graphite.evaluator.BuiltinFunction
import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType

class BuiltinObject(val fn: BuiltinFunction) : Object {
    override fun type(): ObjectType = ObjectType.BUILTIN
    override fun inspect(): String = "builtin function"
}