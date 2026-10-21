package com.example.devices.data

enum class EstadoVotacion { PROGRAMADA, ACTIVA, FINALIZADA, CANCELADA }

data class Opcion(
    val id: Int,
    val nombre: String
)

data class Votacion(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fechaInicio: String,   // texto por ahora; el backend enviará fechas ISO
    val fechaFin: String,
    val estado: EstadoVotacion,
    val opciones: List<Opcion>
)