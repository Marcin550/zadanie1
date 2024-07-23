package com.example.demo.repository

import com.example.demo.Entity.RepositoryEntity
import com.example.demo.Entity.RepositoryEntityList
import com.example.demo.exception.RepositoryNotFoundException
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDate

private const val BASE_URL = "https://api.github.com/"

@Repository
class RepositoriesRepository {

    private val webClient = WebClient.builder()
        .baseUrl(BASE_URL)
        .build()

    fun searchRepositories(size:Int, date: LocalDate, language:String): RepositoryEntityList {
        val query = getQuery(date, language)
        return searchByQuery(query, size)
    }

    fun searchRepositories(size:Int, date: LocalDate): RepositoryEntityList {
        val query = getQuery(date)
        return searchByQuery(query, size)
    }

    fun getRepositoryById(id:Int): RepositoryEntity {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("repositories/$id")
                    .build()
            }
            .retrieve()
            .onStatus(HttpStatusCode::isError) { Mono.error(RepositoryNotFoundException()) }
            .toEntity(RepositoryEntity::class.java).block()?.body ?: throw RepositoryNotFoundException()

    }

    private fun searchByQuery(query: String, size: Int): RepositoryEntityList {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("search/repositories")
                    .queryParam("q", query)
                    .queryParam("order", "desc")
                    .queryParam("per_page", size)
                    .build()
            }
            .retrieve()
            .onStatus(HttpStatusCode::isError) { Mono.empty() }
            .toEntity(RepositoryEntityList::class.java)
            .block()?.body ?: RepositoryEntityList.empty()
    }

    private fun getQuery(date: LocalDate): String {
        return "created:>$date"
    }

    private fun getQuery(date: LocalDate, language: String): String {
        return "created:>$date language:$language"
    }


}