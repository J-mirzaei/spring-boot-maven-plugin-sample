package org.example.example_client

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
class ExampleClientTest {

    @Mock
    lateinit var restTemplate: RestTemplate

    @InjectMocks
    lateinit var exampleClient: ExampleClient

    @Test
    fun `Get environment happy flow`() {
        whenever(restTemplate.getForObject("/environments", EnvironmentResponse::class.java))
            .thenReturn(EnvironmentResponse("environment"))

        exampleClient.getEnvironments()

        verify(restTemplate).getForObject("/environments", EnvironmentResponse::class.java)
    }

    @Test
    fun `Get environment unhappy flow`() {
        whenever(restTemplate.getForObject("/environments", EnvironmentResponse::class.java))
            .thenThrow(RestClientException("No body"))

        assertThrows<RestClientException> { exampleClient.getEnvironments() }
    }
}
