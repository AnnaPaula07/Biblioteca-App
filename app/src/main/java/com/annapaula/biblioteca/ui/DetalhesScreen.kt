package com.annapaula.biblioteca.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.annapaula.biblioteca.model.Livro

@Composable
fun DetalhesScreen(
    livro: Livro?,
    onVoltar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(onClick = onVoltar) {
            Text("← Voltar")
        }

        if (livro == null) {
            Text(
                text = "Não foi possível carregar este livro. Volte e faça a busca novamente.",
                color = MaterialTheme.colorScheme.error
            )
            return@Column
        }

        val capa = livro.urlCapa("L")
        if (capa != null) {
            AsyncImage(
                model = capa,
                contentDescription = "Capa do livro ${livro.titulo}",
                modifier = Modifier
                    .height(280.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(text = livro.titulo, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Autor(es): " + (livro.autores?.joinToString(", ") ?: "desconhecido"),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Primeira publicação: " + (livro.anoPublicacao?.toString() ?: "não informada"),
            style = MaterialTheme.typography.bodyLarge
        )

        val assuntos = livro.assuntos?.take(8)
        if (!assuntos.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Assuntos", style = MaterialTheme.typography.titleMedium)
            Text(text = assuntos.joinToString(", "), style = MaterialTheme.typography.bodyMedium)
        }
    }
}