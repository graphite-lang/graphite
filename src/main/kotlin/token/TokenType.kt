package org.graphite.token

enum class TokenType {
    ILLEGAL,

    EOF,
    EOL,

    IDENTIFIER,
    INT,
    STRING,

    TRUE,
    FALSE,

    NULL,

    BANG,
    ASSIGN,
    EQUAL,
    NOT_EQUAL,

    LESS_THAN,
    GREATER_THAN,

    PLUS,
    MINUS,
    ASTERISK,
    SLASH,

    SEMICOLON,
    COMMA,

    LEFT_PARENTHESES,
    RIGHT_PARENTHESES,
    LEFT_BRACE,
    RIGHT_BRACE,

    FN,
    RETURN,

    IF,
    ELSE,

    CONST,
    VAR,
}

val keywords =
    mapOf(
        "fn" to TokenType.FN,
        "return" to TokenType.RETURN,
        "if" to TokenType.IF,
        "else" to TokenType.ELSE,
        "const" to TokenType.CONST,
        "var" to TokenType.VAR,
        "true" to TokenType.TRUE,
        "false" to TokenType.FALSE,
        "null" to TokenType.NULL,
    )
