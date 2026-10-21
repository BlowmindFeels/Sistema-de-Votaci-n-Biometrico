package com.example.devices.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import  com.example.devices.data.EstadoVotacion

@Composable
fun EstadoBadge(estado: EstadoVotacion) {
    val cs = MaterialTheme.colorScheme
    val (texto, fondo, contenido) = when (estado) {
        EstadoVotacion.ACTIVA -> Triple("Activa", cs.primaryContainer, cs.onPrimaryContainer)
        EstadoVotacion.PROGRAMADA -> Triple("Programada", cs.tertiaryContainer, cs.onTertiaryContainer)
        EstadoVotacion.FINALIZADA -> Triple("Finalizada", cs.surfaceVariant, cs.onSurfaceVariant)
        EstadoVotacion.CANCELADA -> Triple("Cancelada", cs.errorContainer, cs.onErrorContainer)
    }

    Surface(color = fondo, contentColor = contenido, shape = RoundedCornerShape(50)) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}