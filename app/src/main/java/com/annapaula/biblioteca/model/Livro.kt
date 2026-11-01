package com.annapaula.biblioteca.model

import com.google.gson.annotations.SerializedName

data class RespostaBusca(
    @SerializedName("numFound") val totalEncontrado: Int,
    @SerializedName("docs") val livros: List<Livro>
)

data class Livro(
    @SerializedName("key") val chave: String,
    @SerializedName("title") val titulo: String,
    @SerializedName("author_name") val autores: List<String>?,
    @SerializedName("first_publish_year") val anoPublicacao: Int?,
    @SerializedName("cover_i") val capaId: Int?,
    @SerializedName("subject") val assuntos: List<String>?
) {
    fun urlCapa(tamanho: String = "M"): String? =
        capaId?.let { "https://covers.openlibrary.org/b/id/$it-$tamanho.jpg" }

    fun idCurto(): String = chave.removePrefix("/works/")
}