package org.graphite.evaluator

import org.graphite.`object`.Object
import org.graphite.`object`.objects.ErrorObject
import org.graphite.`object`.objects.IntObject
import org.graphite.`object`.objects.StringObject

typealias BuiltinFunction = (args: List<Object>) -> Object

val builtins: Map<String, BuiltinFunction> = mapOf(
    "len" to { args ->
        if (args.size != 1) {
            ErrorObject("wrong number of arguments. got=${args.size}, want=1")
        }

        when (args[0]) {
            is StringObject -> IntObject((args[0] as StringObject).value.length)

            else -> ErrorObject("argument to `len` not supported, got ${args[0].type()}")
        }
    },

    "print" to { args ->
        args.forEach { println(it.inspect()) }
        ObjectConstants.NULL
    }
)