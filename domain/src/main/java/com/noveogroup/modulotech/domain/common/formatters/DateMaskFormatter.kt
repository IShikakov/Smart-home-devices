package com.noveogroup.modulotech.domain.common.formatters

class DateMaskFormatter(private val pattern: String) {

    private val separator = pattern.first { !it.isLetterOrDigit() }
    val maxNumberDigits = pattern.count { it != separator }
    val maskLength = pattern.length

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

    fun extractDateDigits(input: String): String =
        input.filter { it.isDigit() }.take(maxNumberDigits)
}
