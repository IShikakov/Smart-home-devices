package com.noveogroup.modulotech.ui.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Changes visual output of the input field by adding a separator between parts of the date.
 * Currently only dd/MM/yyyy format patterns are supported, with any single character separator.
 */
class DateTransformation(private val pattern: String) : VisualTransformation {

    private val separator = pattern.first { !it.isLetterOrDigit() }
    private val numberSeparators = pattern.count { it == separator }
    private val maxNumberDigits = pattern.length - numberSeparators

    private val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset + 1
            if (offset <= 8) return offset + 2
            return pattern.length
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            return maxNumberDigits
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        return text.transform()
    }

    private fun AnnotatedString.transform(): TransformedText {
        val trimmedText = if (text.length >= maxNumberDigits) {
            text.substring(0 until maxNumberDigits)
        } else {
            text
        }
        val output = buildString {
            var maskIndex = 0
            for (char in pattern) {
                when {
                    char == separator -> append(separator)
                    maskIndex >= trimmedText.length -> break
                    else -> {
                        append(trimmedText[maskIndex])
                        maskIndex++
                    }
                }
            }
        }

        return TransformedText(AnnotatedString(output), numberOffsetTranslator)
    }
}