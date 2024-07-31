package br.com.eas.lockthings.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LockViewModel : ViewModel() {

    private val _lockState = MutableLiveData<Boolean>()
    val lockState: LiveData<Boolean> get() = _lockState

    private val _countdown = MutableLiveData<Int>()
    val countdown: LiveData<Int> get() = _countdown

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    internal var assignedNfcTag: String? = null
    var assigningKey: Boolean = false
    private var unlockJob: Job? = null

    fun assignKey(tag: String?) {
        if (tag != null) {
            assignedNfcTag = tag
            _message.value = "Chave atribuída com sucesso!"
            _lockState.value = false
        } else {
            assigningKey = true
            _message.value = "Aproxime a tag NFC para atribuir a chave."
        }
    }

    fun unlock(tag: String) {
        if (tag == assignedNfcTag) {
            _message.value = "Cadeado aberto com sucesso!"
            _lockState.value = true
            unlockJob?.cancel()
        } else {
            _message.value = "Chave incorreta!"
            _lockState.value = false
        }
        assigningKey = false
    }

    fun lock() {
        _lockState.value = false
        _message.value = "Cadeado fechado"
        unlockJob?.cancel()
    }

    fun startUnlockCountdown() {
        _countdown.value = 30
        unlockJob?.cancel()

        unlockJob = viewModelScope.launch {
            for (i in 30 downTo 0) {
                _countdown.value = i
                delay(1000)
            }
            _message.value = "Tempo esgotado. Tente novamente"
            _lockState.value = false
        }
    }
}
