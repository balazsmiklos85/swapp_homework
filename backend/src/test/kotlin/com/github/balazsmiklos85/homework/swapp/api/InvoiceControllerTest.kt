package com.github.balazsmiklos85.homework.swapp.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
class InvoiceControllerTest() {
    private lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                 .build()
    }

    @Test
    fun `Fetching data`() {
        mockMvc.perform(get("/data"))
                .andExpect(status().isOk)
    }

    @Test
    fun `Creating invoice`() {
        val requestBody = """
            [{"name": "Hamburger"}]
        """.trimIndent()
        mockMvc.perform(post("/invoice").contentType(MediaType.APPLICATION_JSON)
                                                   .content(requestBody))
               .andExpect(status().isOk)
               .andExpect(jsonPath("$.id").exists())
    }
}
