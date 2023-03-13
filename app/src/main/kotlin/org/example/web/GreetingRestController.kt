package org.example.web

import org.example.example_client.ExampleClient
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingRestController(
    private val greetingProperties: GreetingProperties,
    private val exampleClient: ExampleClient
) {

    private val log by lazy { LoggerFactory.getLogger(javaClass) }

    @GetMapping("/greetings")
    fun getGreeting(): ResponseEntity<String> {
        val environments = exampleClient.getEnvironments()
        val greeting = greetingProperties.property
        log.info("Responding with greeting [{}] and environments [{}]", greeting, environments)
        return ResponseEntity.ok("$greeting, $environments")
    }
}
