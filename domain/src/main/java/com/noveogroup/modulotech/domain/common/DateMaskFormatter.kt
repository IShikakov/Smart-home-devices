package com.noveogroup.modulotech.domain.common

class DateMaskFormatter(private val pattern: String) {

    private val separator = pattern.first { !it.isLetterOrDigit() }
    private val maxNumberDigits = pattern.filter { it != separator }.length

    fun format(input: String): String {
        val filteredText = input.filter { it.isDigit() }.take(maxNumberDigits)
        return buildString {
            var maskIndex = 0
            for (char in pattern) {
                when {
                    char == separator -> append(separator)
                    maskIndex >= filteredText.length -> break
                    else -> {
                        append(filteredText[maskIndex])
                        maskIndex++
                    }
                }
            }
        }
    }
}