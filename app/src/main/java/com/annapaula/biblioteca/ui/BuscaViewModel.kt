package com.annapaula.biblioteca.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annapaula.biblioteca.api.RetrofitClient
import kotlinx.coroutines.launch
import java.io.IOException

class BuscaViewModel : ViewModel() {

    var estado by mutableStateOf<BuscaUiState>(BuscaUiState.Inicial)
        private set

    fun buscar(termo: String) {
        val termoLimpo = termo.trim()

        // Validação da entrada
        if (termoLimpo.length < 2) {
            estado = BuscaUiState.Erro("Digite o nome do livro para buscar.")
            return
        }

        estado = BuscaUiState.Carregando

        viewModelScope.launch {
            estado = try {
                val resposta = RetrofitClient.api.buscarLivros(termoLimpo)
                if (resposta.livros.isEmpty()) {
                    BuscaUiState.Erro("Nenhum livro encontrado para \"$termoLimpo\".")
                } else {
                    BuscaUiState.Sucesso(resposta.livros)
                }
            } catch (e: IOException) {
                BuscaUiState.Erro("Sem conexão com a internet. Verifique a rede e tente novamente.")
            } catch (e: Exception) {
                BuscaUiState.Erro("Não foi possível buscar os livros. Tente novamente.")
            }
        }
    }
}