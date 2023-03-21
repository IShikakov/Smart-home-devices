package com.decathlon.coach.domain

import com.noveogroup.modulotech.domain.common.DateMaskFormatter
import org.junit.Assert
import org.junit.Test

class DateMaskFormatterTest {

    @Test
    fun `Format a valid date string`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output = formatter.format("10/12/2020")

        Assert.assertEquals("10/12/2020", output)
    }

    @Test
    fun `Format a date string with invalid characters`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output1 = formatter.format("1a/12d/2020")
        val output2 = formatter.format("10/12/a.a ")

        Assert.assertEquals("11/22/020", output1)
        Assert.assertEquals("10/12/", output2)
    }

    @Test
    fun `Format digits string`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output = formatter.format("10122020")

        Assert.assertEquals("10/12/2020", output)

    }

    @Test
    fun `Format an unfilled date string`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output = formatter.format("10/12/20")

        Assert.assertEquals("10/12/20", output)

    }

    @Test
    fun `Format a date string without year`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output1 = formatter.format("10/12/")
        val output2 = formatter.format("10/12")

        Assert.assertEquals("10/12/", output1)
        Assert.assertEquals("10/12/", output2)
    }

    @Test
    fun `Format a date string that contains only a day`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output1 = formatter.format("10/")
        val output2 = formatter.format("10")

        Assert.assertEquals("10/", output1)
        Assert.assertEquals("10/", output2)
    }

    @Test
    fun `Format a date string that contains more characters than expected`() {
        val formatter = DateMaskFormatter("dd/MM/yyyy")

        val output1 = formatter.format("10/12/20202")
        val output2 = formatter.format("101220202")

        Assert.assertEquals("10/12/2020", output1)
        Assert.assertEquals("10/12/2020", output2)
    }
}