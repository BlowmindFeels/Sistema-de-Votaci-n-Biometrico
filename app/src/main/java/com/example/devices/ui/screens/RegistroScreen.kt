package com.example.devices.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devices.ui.components.CampoPassword
import com.example.devices.ui.components.CampoTexto
import com.example.devices.viewmodel.RegistroViewModel

@Composable
fun RegistroScreen(
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit,
    viewModel: RegistroViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.registroExitoso) {
        if (state.registroExitoso) onRegistroExitoso()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        CampoTexto(state.nombre, viewModel::onNombreChange, "Nombre completo", state.errorNombre)
        Spacer(Modifier.height(8.dp))
        CampoTexto(
            state.documento, viewModel::onDocumentoChange, "Documento",
            state.errorDocumento, KeyboardType.Number
        )
        Spacer(Modifier.height(8.dp))
        CampoTexto(
            state.correo, viewModel::onCorreoChange, "Correo electrónico",
            state.errorCorreo, KeyboardType.Email
        )
        Spacer(Modifier.height(8.dp))
        CampoPassword(state.password, viewModel::onPasswordChange, "Contraseña", state.errorPassword)

        state.errorGeneral?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = viewModel::registrar,
            enabled = !state.cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Registrarme")
            }
        }

        TextButton(onClick = onVolver, enabled = !state.cargando) {
            Text("Ya tengo cuenta")
        }
    }
}