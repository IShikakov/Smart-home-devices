package com.noveogroup.modulotech.domain.common

class DateMaskFormatter(private val pattern: String) {

    private val separator = pattern.first { !it.isLetterOrDigit() }
    private val maxNumberDigits = pattern.filter { it != separator }.length

    fun format(input: String): String {
        val dateDigits = extractDateDigits(input)
        return buildString {
            var maskIndex = 0
            for (char in pattern) {
                when {
                    char == separator -> append(separator)
                    maskIndex >= dateDigits.length -> break
                    else -> {
                        append(dateDigits[maskIndex])
                        maskIndex++
                    }
                }
            }
        }
    }

    fun extractDateDigits(input: String): String {
        return input.filter { it.isDigit() }.take(maxNumberDigits)
    }
}