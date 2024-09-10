package org.graphite.token

data class Token(val type: TokenType, val literal: String) {
    override fun toString(): String {
        return "Token(type: $type, literal: '$literal')"
    }
}
