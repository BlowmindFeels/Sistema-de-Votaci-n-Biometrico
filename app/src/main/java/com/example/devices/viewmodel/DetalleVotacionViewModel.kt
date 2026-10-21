package com.example.devices.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.devices.data.FakeVotacionRepository
import com.example.devices.data.Votacion
import com.example.devices.data.VotacionRepository

data class DetalleUiState(
    val cargando: Boolean = true,
    val votacion: Votacion? = null,
    val opcionSeleccionada: Int? = null,
    val yaVoto: Boolean = false,
    val mostrarConfirmacion: Boolean = false,
    val enviando: Boolean = false,
    val error: String? = null
)

class DetalleVotacionViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val repo: VotacionRepository = FakeVotacionRepository
    private val votacionId: Int = checkNotNull(savedStateHandle.get<Int>("votacionId"))

    private val _state = MutableStateFlow(DetalleUiState())
    val state: StateFlow<DetalleUiState> = _state.asStateFlow()

    init { cargar() }

    private fun cargar() {
        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null) }
            repo.obtener(votacionId)
                .onSuccess { v ->
                    val yaVoto = repo.yaVoto(votacionId)
                    _state.update { it.copy(cargando = false, votacion = v, yaVoto = yaVoto) }
                }
                .onFailure { e -> _state.update { it.copy(cargando = false, error = e.message) } }
        }
    }

    fun seleccionarOpcion(id: Int) =
        _state.update { it.copy(opcionSeleccionada = id, error = null) }

    fun pedirConfirmacion() {
        if (_state.value.opcionSeleccionada != null) {
            _state.update { it.copy(mostrarConfirmacion = true) }
        }
    }

    fun cancelarConfirmacion() =
        _state.update { it.copy(mostrarConfirmacion = false) }

    fun confirmarVoto() {
        val opcionId = _state.value.opcionSeleccionada ?: return
        viewModelScope.launch {
            _state.update { it.copy(mostrarConfirmacion = false, enviando = true, error = null) }

            // PASO 3: aquí se pedirá la huella antes de llamar a repo.votar()

            repo.votar(votacionId, opcionId)
                .onSuccess { _state.update { it.copy(enviando = false, yaVoto = true) } }
                .onFailure { e -> _state.update { it.copy(enviando = false, error = e.message) } }
        }
    }
}