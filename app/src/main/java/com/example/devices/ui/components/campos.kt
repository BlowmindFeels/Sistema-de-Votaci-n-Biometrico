package com.example.devices.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.OutlinedTextFieldDefaults


@Composable
private fun coloresCampo(): TextFieldColors =
    OutlinedTextFieldDefaults.colors(
        // Texto que escribe el usuario
        focusedTextColor = Color(0xFF1B1B1F),
        unfocusedTextColor = Color(0xFF1B1B1F),

        // Etiqueta (el "Documento" / "Contraseña" flotante)
        focusedLabelColor = Color(0xFF1565C0),
        unfocusedLabelColor = Color(0xFF666666),

        // Borde
        focusedBorderColor = Color(0xFF1565C0),
        unfocusedBorderColor = Color(0xFF999999),

        // Cursor
        cursorColor = Color(0xFF1565C0)
    )

@Composable
fun CampoTexto(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String?,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
        colors = coloresCampo(),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CampoPassword(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String?,
) {
    var visible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
        isError = error != null,
        colors = coloresCampo(),
        supportingText = { error?.let { Text(it) } },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            TextButton(onClick = { visible = !visible }) {
                Text(if (visible) "Ocultar" else "Mostrar")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}