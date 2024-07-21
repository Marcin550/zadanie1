package com.example.demo.service

import com.example.demo.Entity.RepositoryEntity
import com.example.demo.Entity.RepositoryEntityList
import com.example.demo.exception.RepositoryNotFoundException
import com.example.demo.exception.WrongDateFormatException
import com.example.demo.repository.RepositoriesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class RepositoriesServiceImpl(@Autowired val repositoriesRepository: RepositoriesRepository) : RepositoriesService {

    private val savedRepositories:MutableMap<Int, RepositoryEntity> = mutableMapOf()
    private val dateFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun searchRepositories(date: String, size: Int): RepositoryEntityList {
        try {
            val formattedDate = LocalDate.parse(date, dateFormatter)
            return repositoriesRepository.searchRepositories(size, formattedDate)
        }catch (exception: DateTimeException){
            throw WrongDateFormatException()
        }
    }

    override fun searchRepositories(date:String, size:Int, language:String): RepositoryEntityList {
        try {
            val formattedDate = LocalDate.parse(date, dateFormatter)
            return repositoriesRepository.searchRepositories(size, formattedDate, language)
        }catch (exception: DateTimeException){
            throw WrongDateFormatException()
        }
    }

    override fun getRepositoryById(id: Int): RepositoryEntity {
        return repositoriesRepository.getRepositoryById(id)
    }

    override fun saveRepositoryById(id: Int): RepositoryEntity {
        val searchedRepository = repositoriesRepository.getRepositoryById(id)
        savedRepositories.put(id, searchedRepository)
        return searchedRepository
    }

    override fun deleteSavedRepositoryById(id: Int): RepositoryEntity {
        return savedRepositories.remove(id) ?: throw RepositoryNotFoundException()

    }

    override fun deleteAllSavedRepositories() {
        savedRepositories.clear()
    }

    override fun getSavedRepositories(): List<RepositoryEntity> {
        return savedRepositories.values.toList()
    }

}