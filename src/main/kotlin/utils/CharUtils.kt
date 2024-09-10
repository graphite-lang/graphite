package org.graphite.utils

class CharUtils {
    companion object {
        fun isLetter(char: Char?): Boolean {
            return char in 'a'..'z' || char in 'A'..'Z' || char == '_'
        }

        fun isDigit(char: Char?): Boolean {
            return char in '0'..'9'
        }
    }
}
