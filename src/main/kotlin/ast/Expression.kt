package org.graphite.ast

import org.graphite.token.Token

interface Expression : Node {
    val token: Token
}
