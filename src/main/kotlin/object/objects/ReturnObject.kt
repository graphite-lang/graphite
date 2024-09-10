package org.graphite.`object`.objects

import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType

class ReturnObject(val value: Object) : Object {
    override fun type(): ObjectType = ObjectType.RETURN
    override fun inspect(): String = value.inspect()
}