package com.example.demo.integration

import com.example.demo.integration.ControllerIntegrationTestsConstants.DATE_PARAM
import com.example.demo.integration.ControllerIntegrationTestsConstants.DATE_VALUE
import com.example.demo.integration.ControllerIntegrationTestsConstants.DEFAULT_SIZE_VALUE
import com.example.demo.integration.ControllerIntegrationTestsConstants.ID_PARAM
import com.example.demo.integration.ControllerIntegrationTestsConstants.LANGUAGE_PARAM
import com.example.demo.integration.ControllerIntegrationTestsConstants.LANGUAGE_VALUE
import com.example.demo.integration.ControllerIntegrationTestsConstants.REPOSITORIES_PATH
import com.example.demo.integration.ControllerIntegrationTestsConstants.REPOSITORIES_PATH_ID
import com.example.demo.integration.ControllerIntegrationTestsConstants.REPOSITORIES_PATH_ID_SAVE
import com.example.demo.integration.ControllerIntegrationTestsConstants.REPOSITORIES_PATH_SAVED
import com.example.demo.integration.ControllerIntegrationTestsConstants.REPOSITORIES_SEARCH_PATH
import com.example.demo.integration.ControllerIntegrationTestsConstants.SIZE_PARAM
import com.example.demo.integration.ControllerIntegrationTestsConstants.SIZE_VALUE
import com.example.demo.integration.ControllerIntegrationTestsConstants.TEST_REPOSITORY_ID
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import kotlin.test.Test


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepositoriesControllerIntegrationTests(
    @LocalServerPort private val port: Int
) {

    @Test
    fun shouldReturnRepositories() {
        Given {
            port(port)
            param(DATE_PARAM, "2019-01-10")
        } When {
            get(REPOSITORIES_SEARCH_PATH)
        }Then {
            statusCode(HttpStatus.OK.value())
            body("items.size()", equalTo(DEFAULT_SIZE_VALUE))
        }
    }

    @Test
    fun shouldReturnTwoRepositories() {
        Given {
            port(port)
            param(DATE_PARAM, DATE_VALUE)
            param(SIZE_PARAM, SIZE_VALUE)
        } When {
            get(REPOSITORIES_SEARCH_PATH)
        }Then {
            statusCode(HttpStatus.OK.value())
            body("items.size()", equalTo(SIZE_VALUE))
        }
    }

    @Test
    fun shouldReturnJavaRepositories() {
        Given {
            port(port)
            param(DATE_PARAM, DATE_VALUE)
            param(LANGUAGE_PARAM, LANGUAGE_VALUE)
        } When {
            get(REPOSITORIES_SEARCH_PATH)
        }Then {
            statusCode(HttpStatus.OK.value())
            body("items[0].language", equalTo(LANGUAGE_VALUE))
        }
    }

    @Test
    fun shouldReturnRepository() {
        Given {
            port(port)
            pathParam(ID_PARAM, TEST_REPOSITORY_ID)
        } When {
            get(REPOSITORIES_PATH_ID)
        }Then {
            statusCode(HttpStatus.OK.value())
            body(ID_PARAM, equalTo(TEST_REPOSITORY_ID))
        }
    }

    @Test
    fun shouldSaveRepository() {
        Given {
            port(port)
            pathParam(ID_PARAM, TEST_REPOSITORY_ID)
        } When {
            put(REPOSITORIES_PATH_ID_SAVE)
        }Then {
            statusCode(HttpStatus.OK.value())
            body(ID_PARAM, equalTo(TEST_REPOSITORY_ID))
        }

        Given {
            port(port)
        } When {
            get(REPOSITORIES_PATH_SAVED)
        }Then {
            statusCode(HttpStatus.OK.value())
            body("items[0].id", equalTo(TEST_REPOSITORY_ID))
            body("total_count", equalTo(1))

        }
    }

    @Test
    fun shouldRemoveSavedRepository() {
        Given {
            port(port)
            pathParam(ID_PARAM, TEST_REPOSITORY_ID)
        } When {
            put(REPOSITORIES_PATH_ID_SAVE)
        }Then {
            statusCode(HttpStatus.OK.value())
            body(ID_PARAM, equalTo(TEST_REPOSITORY_ID))
        }

        Given {
            port(port)
        } When {
            delete(REPOSITORIES_PATH_SAVED)
        }Then {
            statusCode(HttpStatus.OK.value())
        }

        Given {
            port(port)
        } When {
            get(REPOSITORIES_PATH_SAVED)
        }Then {
            statusCode(HttpStatus.OK.value())
            body("total_count", equalTo(0))

        }
    }
}