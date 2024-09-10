package org.graphite.`object`.objects

import org.graphite.`object`.Object
import org.graphite.`object`.ObjectType

class ErrorObject(val message: String) : Object {
    override fun type(): ObjectType = ObjectType.ERROR
    override fun inspect(): String = "Error: $message"
}