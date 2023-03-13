package org.example.example_client

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Component
class ExampleClient(
    private val restTemplate: RestTemplate
) {

    fun getEnvironments(): String {
        val response = restTemplate.getForObject<EnvironmentResponse>("/environments")

        return response.cfEnv
    }
}

data class EnvironmentResponse(
    val cfEnv: String
)
