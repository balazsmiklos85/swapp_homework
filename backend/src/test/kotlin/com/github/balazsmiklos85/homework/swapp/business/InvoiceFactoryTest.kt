package com.github.balazsmiklos85.homework.swapp.business

import com.github.balazsmiklos85.homework.swapp.data.InvoiceSelection
import com.github.balazsmiklos85.homework.swapp.error.ItemNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

class InvoiceFactoryTest {
    private lateinit var invoiceFactory: InvoiceFactory

    @Mock
    private lateinit var mockDataService: DataService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(mockDataService.getAllRows()).thenCallRealMethod()
        this.invoiceFactory = InvoiceFactory(mockDataService)
    }

    @Test
    fun `Throwing exception for invalid input`() {
        try {
            val invalidInput = listOf(InvoiceSelection("Hamburger"),
                                      InvoiceSelection("invalid name"))
            invoiceFactory.create(invalidInput)
            fail("Exception expected, but not thrown.")
        } catch (ignored : ItemNotFoundException) {
            // as expected
        }
    }

    @Test
    fun `Calculating total`() {
        val invalidInput = listOf(InvoiceSelection("Hamburger"),
                                  InvoiceSelection("Small Fries"))
        val result = invoiceFactory.create(invalidInput)
        assertEquals(BigDecimal("7.83"), result.total)
    }
}