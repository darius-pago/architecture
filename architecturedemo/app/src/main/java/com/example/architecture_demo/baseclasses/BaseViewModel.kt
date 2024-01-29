package com.example.architecture_demo.baseclasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface IBaseViewModel<STATE, ACTION, EFFECT> {
    val uiState: StateFlow<STATE>
    val effect: SharedFlow<EFFECT>
    fun processUiAction(action: ACTION)
}

abstract class BaseViewModel<STATE, ACTION, EFFECT, RESULT>(
    private val interactor: BaseInteractor<ACTION, RESULT>,
    initialUiState: STATE
) : IBaseViewModel<STATE, ACTION, EFFECT>,
    ViewModel() {

    private val _effect: MutableSharedFlow<EFFECT> = MutableSharedFlow()
    private val _actionToResultFlow: MutableSharedFlow<RESULT> = MutableSharedFlow()

    override val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()
    override val uiState: StateFlow<STATE> = merge(_actionToResultFlow, interactor.initResults())
        .scan(initialUiState, ::handleResult)
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialUiState)

    override fun processUiAction(action: ACTION) {
        viewModelScope.launch {
            val result = interactor.actionToResult(action)
            _actionToResultFlow.emitAll(result)
        }
    }

    suspend fun emmitEffect(effect: EFFECT) {
        _effect.emit(effect)
    }

    abstract suspend fun handleResult(previousState: STATE, result: RESULT): STATE
}