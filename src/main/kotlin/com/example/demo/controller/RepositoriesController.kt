package com.example.demo.controller

import com.example.demo.Entity.RepositoryEntity
import com.example.demo.Entity.RepositoryEntityList
import com.example.demo.service.RepositoriesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("repositories")
class RepositoriesController(@Autowired val repositoriesService: RepositoriesService) {


    @GetMapping("/search")
    fun search(@RequestParam date: String,
               @RequestParam(defaultValue = "10") size: Int,
               @RequestParam language: String?
    ): RepositoryEntityList {
        if (language != null) {
            return repositoriesService.searchRepositories(date, size, language)
        }
        return repositoriesService.searchRepositories(date, size)
    }


    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): RepositoryEntity {
        return repositoriesService.getRepositoryById(id)
    }

    @PutMapping("/{id}/save")
    fun saveById(@PathVariable id: Int): RepositoryEntity {
        return repositoriesService.saveRepositoryById(id)
    }

    @GetMapping("/saved")
    fun getSaved(): RepositoryEntityList {
        return repositoriesService.getSavedRepositories()
    }

    @DeleteMapping("/{id}")
    fun deleteSaved(@PathVariable id: Int): RepositoryEntity {
        return repositoriesService.deleteSavedRepositoryById(id)
    }
    @DeleteMapping("/")
    fun deleteAllSaved(): Boolean {
        repositoriesService.deleteAllSavedRepositories()
        return true
    }
}