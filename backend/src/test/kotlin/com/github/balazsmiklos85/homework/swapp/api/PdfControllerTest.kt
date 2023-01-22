package com.github.balazsmiklos85.homework.swapp.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class PdfControllerTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                 .build()
    }

    @Test
    fun `Redirecting for root`() {
        mockMvc.perform(get("/"))
               .andExpect(status().is3xxRedirection)
               .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost:3000"))
    }

    @Test
    fun `Fetching non-existent PDF files`() {
        mockMvc.perform(get("/invoices/non-existent-pdf-file-id"))
               .andExpect(status().isNotFound)
    }

    @Test
    fun `Fetching PDF files`() {
        val requestBody = """
                    [{"name": "Hamburger"}]
                """.trimIndent()
        val jsonResult = mockMvc.perform(post("/invoice").contentType(MediaType.APPLICATION_JSON)
                                                                    .content(requestBody))
                                .andReturn()
                                .response
                                .contentAsString
        val id = ObjectMapper().readTree(jsonResult)
                               .get("id")
                               .asText()
        mockMvc.perform(get("/invoices/$id"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_PDF))
    }
}