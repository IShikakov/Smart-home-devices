package com.noveogroup.modulotech.ui.common.formatters

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.noveogroup.modulotech.domain.common.formatters.DateMaskFormatter

/**
 * Changes visual output of the input field by adding a separator between parts of the date.
 * Currently only dd/MM/yyyy format patterns are supported, with any single character separator.
 */
class DateTransformation(pattern: String) : VisualTransformation {

    private val dateMaskFormatter = DateMaskFormatter(pattern)
    private val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset + 1
            if (offset <= 8) return offset + 2
            return dateMaskFormatter.maskLength
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            return dateMaskFormatter.maxNumberDigits
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val output = dateMaskFormatter.format(text.text)
        return TransformedText(AnnotatedString(output), numberOffsetTranslator)
    }
}
