package com.example.demo.service

import com.example.demo.Entity.RepositoryEntity
import com.example.demo.Entity.RepositoryEntityList

interface RepositoriesService {
    fun searchRepositories(date:String, size:Int): RepositoryEntityList
    fun searchRepositories(date:String, size:Int, language:String): RepositoryEntityList
    fun getRepositoryById(id:Int): RepositoryEntity
    fun saveRepositoryById(id:Int): RepositoryEntity
    fun deleteSavedRepositoryById(id:Int): RepositoryEntity
    fun deleteAllSavedRepositories()
    fun getSavedRepositories(): List<RepositoryEntity>
}