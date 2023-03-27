package com.decathlon.coach.domain

import com.noveogroup.modulotech.domain.common.formatters.DateMaskFormatter
import org.junit.Assert
import org.junit.Test

class DateMaskFormatterTest {

    @Test
    fun `DateMaskFormatter must return the same date string if it is already valid`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output = formatter.format("10/12/2020")

        Assert.assertEquals("10/12/2020", output)
    }

    @Test
    fun `DateMaskFormatter must remove all invalid characters if there are any in a date string`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output1 = formatter.format("1a/12d/2020")
        val output2 = formatter.format("10/12/a.a ")

        Assert.assertEquals("11/22/020", output1)
        Assert.assertEquals("10/12/", output2)
    }

    @Test
    fun `DateMaskFormatter must add a separator based on a pattern when a date string contains only digits`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output = formatter.format("10122020")

        Assert.assertEquals("10/12/2020", output)

    }

    @Test
    fun `DateMaskFormatter must add a separator based on a pattern, even if a year part is not finished in a date string`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output = formatter.format("10/12/20")

        Assert.assertEquals("10/12/20", output)

    }

    @Test
    fun `DateMaskFormatter must add a separator based on a pattern, even if a year part is empty in a date string`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output1 = formatter.format("10/12/")
        val output2 = formatter.format("10/12")

        Assert.assertEquals("10/12/", output1)
        Assert.assertEquals("10/12/", output2)
    }

    @Test
    fun `DateMaskFormatter must add a separator based on a pattern, even if a date string contains only a day part`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output1 = formatter.format("10/")
        val output2 = formatter.format("10")

        Assert.assertEquals("10/", output1)
        Assert.assertEquals("10/", output2)
    }

    @Test
    fun `DateMaskFormatter must remove extra characters if a date string contains more characters than a pattern has`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output1 = formatter.format("10/12/20202")
        val output2 = formatter.format("101220202")

        Assert.assertEquals("10/12/2020", output1)
        Assert.assertEquals("10/12/2020", output2)
    }
}