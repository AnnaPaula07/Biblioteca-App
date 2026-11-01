package com.annapaula.biblioteca.ui

import com.annapaula.biblioteca.model.Livro

sealed interface BuscaUiState {
    data object Inicial : BuscaUiState
    data object Carregando : BuscaUiState
    data class Sucesso(val livros: List<Livro>) : BuscaUiState
    data class Erro(val mensagem: String) : BuscaUiState
}