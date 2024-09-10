package org.graphite.`object`.objects

import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType

class NullObject : Object {
    override fun type(): ObjectType = ObjectType.NULL
    override fun inspect(): String = "null"
}