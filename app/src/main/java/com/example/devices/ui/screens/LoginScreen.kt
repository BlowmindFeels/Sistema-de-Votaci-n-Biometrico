package com.example.devices.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devices.ui.components.CampoPassword
import com.example.devices.ui.components.CampoTexto
import com.example.devices.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.loginExitoso) {
        if (state.loginExitoso) onLoginExitoso()
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
        Text("Votación Comunitaria", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Chapinero",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(32.dp))

        CampoTexto(
            valor = state.documento,
            onCambio = viewModel::onDocumentoChange,
            etiqueta = "Documento",
            error = state.errorDocumento,
            tipoTeclado = KeyboardType.Number,

        )
        Spacer(Modifier.height(8.dp))
        CampoPassword(
            valor = state.password,
            onCambio = viewModel::onPasswordChange,
            etiqueta = "Contraseña",
            error = state.errorPassword
        )

        state.errorGeneral?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = viewModel::login,
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
                Text("Ingresar")
            }
        }

        TextButton(onClick = onIrARegistro, enabled = !state.cargando) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}