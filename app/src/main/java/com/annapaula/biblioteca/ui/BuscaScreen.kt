package com.annapaula.biblioteca.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BuscaScreen(
    modifier: Modifier = Modifier,
    viewModel: BuscaViewModel = viewModel()
) {
    var termo by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = termo,
                onValueChange = { termo = it },
                label = { Text("Título, autor ou assunto") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.buscar(termo) }),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.buscar(termo) }) {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val estado = viewModel.estado) {
            is BuscaUiState.Inicial -> {
                Text("Digite algo acima para buscar livros.")
            }

            is BuscaUiState.Carregando -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is BuscaUiState.Erro -> {
                Column {
                    Text(
                        text = estado.mensagem,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.buscar(termo) }) {
                        Text("Tentar novamente")
                    }
                }
            }

            is BuscaUiState.Sucesso -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(estado.livros) { livro ->
                        Column {
                            Text(
                                text = livro.titulo,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = livro.autores?.joinToString(", ") ?: "Autor desconhecido",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}