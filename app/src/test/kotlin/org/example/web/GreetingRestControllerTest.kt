package org.example.web

import org.example.example_client.ExampleClient
import org.example.security.SecurityConfiguration
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(controllers = [GreetingRestController::class])
@ActiveProfiles("integration")
@Import(GreetingConfiguration::class, SecurityConfiguration::class)
class GreetingRestControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {
    @MockBean
    lateinit var exampleClient: ExampleClient

    @Test
    fun `Whenever the greetings-endpoint is called, we get a greeting`() {
        whenever(exampleClient.getEnvironments()).thenReturn("integration")

        mockMvc.get("/greetings")
            .andExpect {
                status {
                    isOk()
                }
            }.andExpect {
                content {
                    string("Hello, integration")
                }
            }
    }
}
