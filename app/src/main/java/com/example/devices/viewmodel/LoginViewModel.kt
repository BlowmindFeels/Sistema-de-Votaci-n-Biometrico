package com.example.devices.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.devices.data.AuthRepository
import com.example.devices.data.FakeAuthRepository
import com.example.devices.util.Validadores

data class LoginUiState(
    val documento: String = "",
    val password: String = "",
    val errorDocumento: String? = null,
    val errorPassword: String? = null,
    val errorGeneral: String? = null,
    val cargando: Boolean = false,
    val loginExitoso: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val repo: AuthRepository = FakeAuthRepository

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onDocumentoChange(v: String) {
        if (v.length <= 10 && v.all { it.isDigit() }) {
            _state.update { it.copy(documento = v, errorDocumento = null, errorGeneral = null) }
        }
    }

    fun onPasswordChange(v: String) {
        _state.update { it.copy(password = v, errorPassword = null, errorGeneral = null) }
    }

    fun login() {
        val s = _state.value
        val errDoc = Validadores.documento(s.documento)
        val errPass = if (s.password.isBlank()) "Ingresa tu contraseña" else null

        if (errDoc != null || errPass != null) {
            _state.update { it.copy(errorDocumento = errDoc, errorPassword = errPass) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(cargando = true, errorGeneral = null) }
            repo.login(s.documento, s.password)
                .onSuccess { _token ->
                    // El token se guardará de forma segura en el paso de biometría.
                    _state.update { it.copy(cargando = false, loginExitoso = true) }
                }
                .onFailure { e ->
                    _state.update { it.copy(cargando = false, errorGeneral = e.message) }
                }
        }
    }
}



