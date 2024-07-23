package com.example.demo.unit.service

import com.example.demo.Entity.RepositoryEntity
import com.example.demo.Entity.RepositoryEntityList
import com.example.demo.exception.WrongDateFormatException
import com.example.demo.repository.RepositoriesRepository
import com.example.demo.service.RepositoriesServiceImpl
import com.example.demo.unit.service.RepositoryServiceTestsConstants.DATE_VALUE
import com.example.demo.unit.service.RepositoryServiceTestsConstants.DEFAULT_SIZE_VALUE
import com.example.demo.unit.service.RepositoryServiceTestsConstants.FIRST_REPOSITORY_ID
import com.example.demo.unit.service.RepositoryServiceTestsConstants.INCORRECT_DATE_VALUE
import com.example.demo.unit.service.RepositoryServiceTestsConstants.FIRST_LANGUAGE_VALUE
import com.example.demo.unit.service.RepositoryServiceTestsConstants.REPOSITORY_NAME_VALUE
import com.example.demo.unit.service.RepositoryServiceTestsConstants.SECOND_LANGUAGE_VALUE
import com.example.demo.unit.service.RepositoryServiceTestsConstants.SECOND_REPOSITORY_ID
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals
import org.mockito.kotlin.any

@ExtendWith(MockitoExtension::class)
class RepositoryServiceTests {
	@Mock private lateinit var repositoriesRepository: RepositoriesRepository
	@InjectMocks lateinit var repositoryService: RepositoriesServiceImpl

	@Test
	fun shouldReturnRepositories() {
		val repositoryEntity = RepositoryEntity(FIRST_REPOSITORY_ID, REPOSITORY_NAME_VALUE, FIRST_LANGUAGE_VALUE)

		val repositoryEntityList =  RepositoryEntityList(1, listOf(repositoryEntity))
		Mockito.`when`(repositoriesRepository.searchRepositories(any(), any()))
			.thenReturn(repositoryEntityList)
		Mockito.`when`(repositoriesRepository.searchRepositories(any(), any(), any()))
			.thenReturn(repositoryEntityList)

		assertEquals(repositoryEntityList, repositoryService.searchRepositories(DATE_VALUE, DEFAULT_SIZE_VALUE))
		assertEquals(repositoryEntityList, repositoryService
			.searchRepositories(DATE_VALUE, DEFAULT_SIZE_VALUE, FIRST_LANGUAGE_VALUE))
	}

	@Test
	fun shouldTrowWrongDateFormatException() {
 		assertThrows(WrongDateFormatException::class.java)
		{ repositoryService.searchRepositories(INCORRECT_DATE_VALUE, 10) }
		assertThrows(WrongDateFormatException::class.java)
		{ repositoryService.searchRepositories(INCORRECT_DATE_VALUE, 10, FIRST_LANGUAGE_VALUE) }

	}

	@Test
	fun shouldSaveRepository() {
		val repositoryEntity = RepositoryEntity(FIRST_REPOSITORY_ID, REPOSITORY_NAME_VALUE, FIRST_LANGUAGE_VALUE)
		Mockito.`when`(repositoriesRepository.getRepositoryById(FIRST_REPOSITORY_ID))
			.thenReturn(repositoryEntity)

		repositoryService.saveRepositoryById(FIRST_REPOSITORY_ID)

		assertEquals(1, repositoryService.getSavedRepositories().total_count)
		assertEquals(repositoryEntity, repositoryService.getSavedRepositories().items[0])

	}

	@Test
	fun shouldSaveTwoRepositories() {
		val firstRepositoryEntity = RepositoryEntity(FIRST_REPOSITORY_ID, REPOSITORY_NAME_VALUE, FIRST_LANGUAGE_VALUE)
		val secondRepositoryEntity = RepositoryEntity(SECOND_REPOSITORY_ID, REPOSITORY_NAME_VALUE, SECOND_LANGUAGE_VALUE)

		Mockito.`when`(repositoriesRepository.getRepositoryById(FIRST_REPOSITORY_ID))
			.thenReturn(firstRepositoryEntity)
		Mockito.`when`(repositoriesRepository.getRepositoryById(SECOND_REPOSITORY_ID))
			.thenReturn(secondRepositoryEntity)

		repositoryService.saveRepositoryById(FIRST_REPOSITORY_ID)
		repositoryService.saveRepositoryById(SECOND_REPOSITORY_ID)
		repositoryService.saveRepositoryById(SECOND_REPOSITORY_ID)


		assertEquals(2, repositoryService.getSavedRepositories().total_count)
		assertEquals(listOf(firstRepositoryEntity, secondRepositoryEntity),
			repositoryService.getSavedRepositories().items)
	}

	@Test
	fun shouldDeleteSecondRepository() {
		val firstRepositoryEntity = RepositoryEntity(FIRST_REPOSITORY_ID, REPOSITORY_NAME_VALUE, FIRST_LANGUAGE_VALUE)
		val secondRepositoryEntity = RepositoryEntity(SECOND_REPOSITORY_ID, REPOSITORY_NAME_VALUE, SECOND_LANGUAGE_VALUE)

		Mockito.`when`(repositoriesRepository.getRepositoryById(FIRST_REPOSITORY_ID))
			.thenReturn(firstRepositoryEntity)
		Mockito.`when`(repositoriesRepository.getRepositoryById(SECOND_REPOSITORY_ID))
			.thenReturn(secondRepositoryEntity)

		repositoryService.saveRepositoryById(FIRST_REPOSITORY_ID)
		repositoryService.saveRepositoryById(SECOND_REPOSITORY_ID)
		repositoryService.deleteSavedRepositoryById(SECOND_REPOSITORY_ID)

		assertEquals(RepositoryEntityList(1, listOf(firstRepositoryEntity)),
			repositoryService.getSavedRepositories())
	}

	@Test
	fun shouldDeleteAllSavedRepositories() {
		val firstRepositoryEntity = RepositoryEntity(FIRST_REPOSITORY_ID, REPOSITORY_NAME_VALUE, FIRST_LANGUAGE_VALUE)
		val secondRepositoryEntity = RepositoryEntity(SECOND_REPOSITORY_ID, REPOSITORY_NAME_VALUE, SECOND_LANGUAGE_VALUE)

		Mockito.`when`(repositoriesRepository.getRepositoryById(FIRST_REPOSITORY_ID))
			.thenReturn(firstRepositoryEntity)
		Mockito.`when`(repositoriesRepository.getRepositoryById(SECOND_REPOSITORY_ID))
			.thenReturn(secondRepositoryEntity)

		repositoryService.saveRepositoryById(FIRST_REPOSITORY_ID)
		repositoryService.saveRepositoryById(SECOND_REPOSITORY_ID)
		repositoryService.deleteAllSavedRepositories()

		assertEquals(0, repositoryService.getSavedRepositories().total_count)
		assertEquals(0, repositoryService.getSavedRepositories().items.size)
	}
}
