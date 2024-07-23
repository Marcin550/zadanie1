package com.example.demo.Entity



data class RepositoryEntityList(val total_count:Int,
                                val items:List<RepositoryEntity> = listOf()){
    companion object {
        fun empty() = RepositoryEntityList(0)
    }
}

