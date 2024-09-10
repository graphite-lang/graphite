package org.graphite.ast

import org.graphite.token.Token

interface Statement : Node {
    val token: Token
}
