package com.example.devices.data


import kotlinx.coroutines.delay

interface AuthRepository {
    suspend fun login(documento: String, password: String): Result<String> // devuelve el token
    suspend fun registrar(nombre: String, documento: String, correo: String, password: String): Result<Unit>
}

// Implementación temporal en memoria. Se reemplaza por Retrofit en el paso de red.
object FakeAuthRepository : AuthRepository {

    private data class Usuario(val nombre: String, val correo: String, val password: String)

    private val usuarios = mutableMapOf(
        "1234567890" to Usuario("Usuario de prueba", "prueba@correo.com", "Clave12345")
    )

    override suspend fun login(documento: String, password: String): Result<String> {
        delay(800) // simula la latencia de red
        val u = usuarios[documento]
        return if (u != null && u.password == password) {
            Result.success("token-falso-$documento")
        } else {
            Result.failure(Exception("Documento o contraseña incorrectos"))
        }
    }

    override suspend fun registrar(
        nombre: String, documento: String, correo: String, password: String
    ): Result<Unit> {
        delay(800)
        if (usuarios.containsKey(documento)) {
            return Result.failure(Exception("Este documento ya está registrado"))
        }
        usuarios[documento] = Usuario(nombre.trim(), correo.trim(), password)
        return Result.success(Unit)
    }
}