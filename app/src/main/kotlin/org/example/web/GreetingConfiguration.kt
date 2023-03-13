package org.example.web

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(GreetingProperties::class)
class GreetingConfiguration

@ConfigurationProperties("app.greeting")
data class GreetingProperties(val property: String)
