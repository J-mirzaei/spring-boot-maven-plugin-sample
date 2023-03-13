package org.example.example_stub

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.ClasspathFileSource
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExampleStubApplication(@Value("\${http.port:8080}") val httpPort: Int) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val options = WireMockConfiguration
            .options()
            .port(httpPort)
            .fileSource(ClasspathFileSource(getWiremockSourceLocation()))
            .enableBrowserProxying(true)

        val wireMockServer = WireMockServer(options)
        wireMockServer.start()
    }

    /**
     * When running the application in your Editor, the Wiremock files are located on classpath:wiremock.
     * When the application is packaged as fat JAR by Spring, the files are moved to BOOT-INF.
     * This method looks up the right location based on the context you are running in.
     *
     * This relates to https://github.com/wiremock/wiremock/issues/725
     */
    private fun getWiremockSourceLocation(): String = if (Thread.currentThread().contextClassLoader.getResource("BOOT-INF") != null) "BOOT-INF/classes/wiremock" else "wiremock"
}

fun main(args: Array<String>) {
    runApplication<ExampleStubApplication>(*args)
}
