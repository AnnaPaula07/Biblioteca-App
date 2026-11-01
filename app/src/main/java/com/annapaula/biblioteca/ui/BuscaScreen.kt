package com.annapaula.biblioteca.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.annapaula.biblioteca.model.Livro

@Composable
fun BuscaScreen(
    viewModel: BuscaViewModel,
    onLivroClick: (Livro) -> Unit,
    modifier: Modifier = Modifier
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
                        LivroItem(livro = livro, onClick = { onLivroClick(livro) })
                    }
                }
            }
        }
    }
}

@Composable
fun LivroItem(livro: Livro, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val capa = livro.urlCapa()
            if (capa != null) {
                AsyncImage(
                    model = capa,
                    contentDescription = "Capa do livro ${livro.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(width = 60.dp, height = 90.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(width = 60.dp, height = 90.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sem capa", style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = livro.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = livro.autores?.joinToString(", ") ?: "Autor desconhecido",
                    style = MaterialTheme.typography.bodyMedium
                )
                livro.anoPublicacao?.let {
                    Text(
                        text = "Publicado em $it",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}