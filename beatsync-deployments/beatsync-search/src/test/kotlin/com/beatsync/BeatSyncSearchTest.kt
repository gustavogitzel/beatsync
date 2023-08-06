package com.beatsync

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.core.env.Environment
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "10000")
class BeatSyncSearchTest(
    private val env: Environment,
    private val webClient: WebTestClient
) : DescribeSpec({

    describe("Test root path") {
        it("Should return 200") {
            webClient
                .get()
                .uri("")
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty
        }
    }

    describe("Test health check") {
        it("Should be healthy") {
            println(env.getProperty("local.management.port"))
            webClient
                .get()
                .uri("http://localhost:${env.getProperty("local.management.port")}/health")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("status").isEqualTo("UP")
        }
    }
})
