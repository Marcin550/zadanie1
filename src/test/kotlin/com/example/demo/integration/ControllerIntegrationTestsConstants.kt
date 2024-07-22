package com.example.demo.integration

object ControllerIntegrationTestsConstants {
    const val TEST_REPOSITORY_ID = 831777478

    const val DATE_PARAM = "date"
    const val SIZE_PARAM = "size"
    const val LANGUAGE_PARAM = "language"
    const val ID_PARAM = "id"

    const val REPOSITORIES_PATH = "repositories"
    const val REPOSITORIES_SEARCH_PATH = "$REPOSITORIES_PATH/search"
    const val REPOSITORIES_PATH_ID = "$REPOSITORIES_PATH/{id}"
    const val REPOSITORIES_PATH_ID_SAVE = "$REPOSITORIES_PATH_ID/save"

    const val DATE_VALUE = "2019-01-10"
    const val SIZE_VALUE = 2
    const val LANGUAGE_VALUE = "Java"

    const val INCORRECT_LANGUAGE_VALUE = "Javaj"
    const val DEFAULT_SIZE_VALUE = 10

}