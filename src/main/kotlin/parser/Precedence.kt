package org.graphite.parser

import org.graphite.token.TokenType

enum class Precedence {
    LOWEST,
    EQUALS,
    LESSGREATER,
    SUM,
    PRODUCT,
    PREFIX,
    CALL,
    ;

    companion object {
        fun precedenceFor(tokenType: TokenType): Precedence {
            return mapOf(
                TokenType.EQUAL to EQUALS,
                TokenType.NOT_EQUAL to EQUALS,
                TokenType.LESS_THAN to LESSGREATER,
                TokenType.GREATER_THAN to LESSGREATER,
                TokenType.PLUS to SUM,
                TokenType.MINUS to SUM,
                TokenType.SLASH to PRODUCT,
                TokenType.ASTERISK to PRODUCT,
                TokenType.LEFT_PARENTHESES to CALL
            ).getOrDefault(key = tokenType, defaultValue = LOWEST)
        }
    }
}
