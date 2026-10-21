package com.example.devices.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devices.data.Votacion
import com.example.devices.ui.components.EstadoBadge
import com.example.devices.viewmodel.VotacionesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VotacionesScreen(
    onVotacionClick: (Int) -> Unit,
    viewModel: VotacionesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val error = state.error

    Scaffold(
        topBar = { TopAppBar(title = { Text("Votaciones") }) }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when {
                state.cargando -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                error != null -> Column(
                    Modifier.align(Alignment.Center).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = viewModel::cargar) { Text("Reintentar") }
                }

                else -> LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.votaciones, key = { it.id }) { votacion ->
                        TarjetaVotacion(votacion) { onVotacionClick(votacion.id) }
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaVotacion(votacion: Votacion, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            EstadoBadge(votacion.estado)
            Spacer(Modifier.height(8.dp))
            Text(votacion.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                votacion.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Del ${votacion.fechaInicio} al ${votacion.fechaFin}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}