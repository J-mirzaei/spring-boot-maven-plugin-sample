package org.example

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class ActuatorIntegrationTest(
    @Autowired private val testRestTemplate: TestRestTemplate,
    @Value("\${spring.security.user.name}") val username: String,
    @Value("\${spring.security.user.password}") val password: String,
) {

    @Test
    fun `Actuator endpoint is secured`() {
        val responseEntity = testRestTemplate.getForEntity<String>("/actuator/env")

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `Health endpoint is publicly available`() {
        val responseEntity = testRestTemplate.getForEntity<String>("/actuator/health")

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(responseEntity.body).isEqualTo("""{"status":"UP"}""")
    }

    @Test
    fun `Actuator endpoint is secured with username and password`() {
        val request = RequestEntity.get("/actuator/env").headers {
            it.setBasicAuth(username, password)
        }.build()

        val responseEntity = testRestTemplate.exchange<String>(request)

        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
    }
}
