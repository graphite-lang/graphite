package org.graphite.lexer

import org.graphite.token.Token
import org.graphite.token.TokenType
import org.graphite.token.keywords
import org.graphite.utils.CharUtils

class Lexer(
    private val input: String,
) {
    var position = Position(line = 1, column = 0)

    var readPosition = 0

    var nextChar: Char? = null
    var char: Char? = null

    private fun currentChar(): Char = input.getOrNull(index = readPosition) ?: 0.toChar()

    private fun eatChar() {
        char = currentChar()

        readPosition += 1
        position.column += 1

        nextChar = currentChar()

    }

    private fun eatWhitespace() {
        while (char!! in charArrayOf(' ', '\t')) {
            eatChar()
        }
    }

    private fun tokenize(char: Char?): Token {
        val token =
            when (char) {
                '!' -> {
                    if (nextChar == '=') {
                        val literal = char.toString() + nextChar

                        // eat !
                        eatChar()

                        Token(type = TokenType.NOT_EQUAL, literal = literal)
                    } else {
                        Token(type = TokenType.BANG, literal = char.toString())
                    }
                }

                '=' -> {
                    if (nextChar == '=') {
                        val literal = char.toString() + nextChar

                        // eat =
                        eatChar()

                        Token(type = TokenType.EQUAL, literal = literal)
                    } else {
                        Token(type = TokenType.ASSIGN, literal = char.toString())
                    }
                }

                '<' -> Token(type = TokenType.LESS_THAN, literal = char.toString())
                '>' -> Token(type = TokenType.GREATER_THAN, literal = char.toString())

                '+' -> Token(type = TokenType.PLUS, literal = char.toString())
                '-' -> Token(type = TokenType.MINUS, literal = char.toString())
                '*' -> Token(type = TokenType.ASTERISK, literal = char.toString())
                '/' -> Token(type = TokenType.SLASH, literal = char.toString())

                ';' -> Token(type = TokenType.SEMICOLON, literal = char.toString())
                ',' -> Token(type = TokenType.COMMA, literal = char.toString())

                '(' -> Token(type = TokenType.LEFT_PARENTHESES, literal = char.toString())
                ')' -> Token(type = TokenType.RIGHT_PARENTHESES, literal = char.toString())

                '{' -> Token(type = TokenType.LEFT_BRACE, literal = char.toString())
                '}' -> Token(type = TokenType.RIGHT_BRACE, literal = char.toString())

                '"' -> {
                    // eat "
                    eatChar()

                    val string = buildString {
                        while (this@Lexer.char != '"') {
                            append(this@Lexer.char)

                            eatChar()
                        }
                    }

                    Token(type = TokenType.STRING, literal = string)
                }

                0.toChar() -> {
                    position.line += 1
                    position.column = 0

                    Token(type = TokenType.EOF, literal = "")
                }

                '\n' -> {
                    position.line += 1
                    position.column = 0

                    Token(type = TokenType.EOL, literal = "")
                }

                else -> {
                    if (CharUtils.isLetter(char = char)) {
                        val identifier =
                            buildString {
                                while (CharUtils.isLetter(char = this@Lexer.char)) {
                                    append(this@Lexer.char)
                                    if (!CharUtils.isLetter(char = nextChar)) {
                                        break
                                    }

                                    eatChar()
                                }
                            }

                        Token(type = keywords[identifier] ?: TokenType.IDENTIFIER, literal = identifier)
                    } else if (CharUtils.isDigit(char = char)) {
                        val number =
                            buildString {
                                while (CharUtils.isDigit(char = this@Lexer.char)) {
                                    append(this@Lexer.char)
                                    if (!CharUtils.isDigit(char = nextChar)) {
                                        break
                                    }

                                    eatChar()
                                }
                            }

                        Token(type = TokenType.INT, literal = number)
                    } else {
                        Token(type = TokenType.ILLEGAL, literal = char.toString())
                    }
                }
            }

        // eat the processed char
        eatChar()
        eatWhitespace()

        return token
    }

    val tokens =
        mutableListOf<Token>()

    fun nextToken() {
        tokens.add(index = tokens.size, tokenize(char = char))
    }

    init {
        eatChar()
        eatWhitespace()
    }
}
