package com.example.devices.util

import android.util.Patterns

object Validadores {
    fun nombre(v: String) =
        if (v.trim().length < 3) "Ingresa tu nombre completo" else null

    fun documento(v: String) =
        if (!v.matches(Regex("^\\d{6,10}$"))) "El documento debe tener entre 6 y 10 dígitos" else null

    fun correo(v: String) =
        if (!Patterns.EMAIL_ADDRESS.matcher(v.trim()).matches()) "Correo no válido" else null

    fun password(v: String) =
        if (v.length < 8) "Mínimo 8 caracteres" else null
}