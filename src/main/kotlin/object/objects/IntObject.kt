package org.graphite.`object`.objects

import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType

class IntObject(val value: Int) : Object {
    override fun type(): ObjectType = ObjectType.INT
    override fun inspect(): String = value.toString()
}