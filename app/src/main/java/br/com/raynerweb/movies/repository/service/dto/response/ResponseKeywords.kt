package br.com.raynerweb.movies.repository.service.dto.response

data class ResponseKeywords(
    val results: List<Keywords>
)

data class Keywords(
    val id: Int,
    val name: String
)