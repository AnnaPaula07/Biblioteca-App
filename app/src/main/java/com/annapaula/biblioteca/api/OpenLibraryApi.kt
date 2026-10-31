package com.annapaula.biblioteca.api
import com.annapaula.biblioteca.model.RespostaBusca
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {

    @GET("search.json")
    suspend fun buscarLivros(
        @Query("q") termo: String,
        @Query("fields") campos: String =
            "key,title,author_name,first_publish_year,cover_i,subject",
        @Query("limit") limite: Int = 20
    ): RespostaBusca
}