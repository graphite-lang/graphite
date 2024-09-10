package org.graphite.`object`.objects

import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType

class BooleanObject(val value: Boolean) : Object {
    override fun type(): ObjectType = ObjectType.BOOLEAN
    override fun inspect(): String = value.toString()
}