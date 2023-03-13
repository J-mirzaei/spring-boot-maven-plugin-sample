package org.example.web

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter
import io.restassured.RestAssured.*
import org.example.example_client.ExampleClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.whenever
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class GreetingApiTest {

    private val log by lazy { LoggerFactory.getLogger(javaClass) }

    @LocalServerPort
    var port = 0
    private var openApiValidationFilter: OpenApiValidationFilter? = null
    private var specFile: File? = null

    @BeforeEach
    fun beforeEach() {
        val spec = spec
        openApiValidationFilter = OpenApiValidationFilter(spec?.absolutePath)
    }

    val spec: File?
        get() {
            if (specFile == null) {
                specFile = File.createTempFile("oas", ".yaml")
                val yaml = given().`when`()["http://localhost:$port/v3/api-docs.yaml"].asByteArray()
                val outputStream: OutputStream
                try {
                    outputStream = FileOutputStream(specFile)
                    outputStream.write(yaml)
                    outputStream.close()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
                log.info("Written {}", specFile?.absolutePath)
            }
            return specFile
        }

    @MockBean
    lateinit var exampleClient: ExampleClient

    @Test
    fun `Get greeting should yield http 200`() {
        whenever(exampleClient.getEnvironments()).thenReturn("integration")
        given()
            .port(port)
            .filter(openApiValidationFilter)
            .`when`()["/greetings"]
            .then()
            .assertThat()
            .statusCode(200)
    }
}
