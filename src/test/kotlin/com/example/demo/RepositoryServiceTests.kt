package com.example.demo

import com.example.demo.Entity.RepositoryEntity
import com.example.demo.Entity.RepositoryEntityList
import com.example.demo.exception.WrongDateFormatException
import com.example.demo.repository.RepositoriesRepository
import com.example.demo.service.RepositoriesServiceImpl
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import org.mockito.kotlin.any

@SpringBootTest
class RepositoryServiceTests {
	private final val repositoriesRepository: RepositoriesRepository = Mockito.mock(RepositoriesRepository::class.java)
	private final val repositoryService = RepositoriesServiceImpl(repositoriesRepository)

	@Test
	fun shouldReturnRepositories() {
		val repositoryEntity = RepositoryEntity(1, "test_name", "Java")

		val repositoryEntityList =  RepositoryEntityList(1, listOf(repositoryEntity))
		Mockito.`when`(repositoriesRepository.searchRepositories(any(), any()))
			.thenReturn(repositoryEntityList)
		Mockito.`when`(repositoriesRepository.searchRepositories(any(), any(), any()))
			.thenReturn(repositoryEntityList)

		assertEquals(repositoryEntityList, repositoryService.searchRepositories("1999-11-01",10))
		assertEquals(repositoryEntityList, repositoryService.searchRepositories("1999-11-01",10, "Java"))
	}

	@Test
	fun shouldTrowWrongDateFormatException() {
 		assertThrows(WrongDateFormatException::class.java)
		{ repositoryService.searchRepositories("1999-112-01", 10) }
		assertThrows(WrongDateFormatException::class.java)
		{ repositoryService.searchRepositories("1999-112-01", 10, "Java") }

	}

	@Test
	fun shouldSaveRepository() {
		val repositoryEntity = RepositoryEntity(1, "test_name", "Java")
		Mockito.`when`(repositoriesRepository.getRepositoryById(any()))
			.thenReturn(repositoryEntity)

		repositoryService.saveRepositoryById(1)

		assertEquals(listOf(repositoryEntity), repositoryService.getSavedRepositories())
	}

	@Test
	fun shouldSaveMultipleRepository() {
		val repositoryEntity = RepositoryEntity(1, "test_name", "Java")
		Mockito.`when`(repositoriesRepository.getRepositoryById(any()))
			.thenReturn(repositoryEntity)

		repositoryService.saveRepositoryById(1)
		repositoryService.saveRepositoryById(2)

		assertEquals(listOf(repositoryEntity, repositoryEntity), repositoryService.getSavedRepositories())
	}

	@Test
	fun shouldDeleteSavedRepository() {
		val repositoryEntity = RepositoryEntity(1, "test_name", "Java")
		Mockito.`when`(repositoriesRepository.getRepositoryById(any()))
			.thenReturn(repositoryEntity)

		repositoryService.saveRepositoryById(1)
		repositoryService.saveRepositoryById(2)
		repositoryService.deleteSavedRepositoryById(2)

		assertEquals(listOf(repositoryEntity), repositoryService.getSavedRepositories())
	}

	@Test
	fun shouldDeleteAllSavedRepositories() {
		val repositoryEntity = RepositoryEntity(1, "test_name", "Java")
		Mockito.`when`(repositoriesRepository.getRepositoryById(any()))
			.thenReturn(repositoryEntity)

		repositoryService.saveRepositoryById(1)
		repositoryService.saveRepositoryById(2)
		repositoryService.deleteAllSavedRepositories()

		assertEquals(listOf(), repositoryService.getSavedRepositories())
	}
}
