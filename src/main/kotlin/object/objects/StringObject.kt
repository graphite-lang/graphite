package org.graphite.`object`.objects

import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType

class StringObject(val value: String) : Object {
    override fun type(): ObjectType = ObjectType.STRING
    override fun inspect(): String = value.toString()
}