package org.example.example_client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(ExampleClientProperties::class)
class ExampleClientConfiguration(
    private val exampleClientProperties: ExampleClientProperties
) {

    @Bean
    fun exampleClientRestTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate =
        restTemplateBuilder
            .rootUri(exampleClientProperties.url)
            .build()
}

@ConfigurationProperties("app.example-client")
data class ExampleClientProperties(
    val url: String
)
