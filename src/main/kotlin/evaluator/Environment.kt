package org.graphite.evaluator

import org.graphite.`object`.Object

class Environment(val outer: Environment? = null) {
    val store: MutableMap<String, Object> = mutableMapOf()

    fun get(name: String): Object? {
        val obj = store.get(key = name)
        if (obj == null && outer != null) {
            return outer.get(name = name)
        }

        return obj
    }

    fun set(name: String, value: Object) {
        store.put(key = name, value = value)
    }
}