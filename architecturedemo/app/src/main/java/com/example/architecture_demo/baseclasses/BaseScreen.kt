package com.example.architecture_demo.baseclasses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.architecture_demo.ui.theme.ArchitecturedemoTheme
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseScreen<STATE : Any, ACTION : Any, EFFECT : Any, VIEW_MODEL>
        where VIEW_MODEL : IBaseViewModel<STATE, ACTION, EFFECT>, VIEW_MODEL : ViewModel {

    @Composable
    fun Content(viewModel: VIEW_MODEL) {
        val state = viewModel.uiState.collectAsState().value
        val effect = viewModel.effect
        val processUiAction: (ACTION) -> Unit = { action -> viewModel.processUiAction(action) }
        ArchitecturedemoTheme {
            Screen(state, effect, processUiAction)
        }
    }

    @Composable
    abstract fun Screen(
        state: STATE,
        effect: SharedFlow<EFFECT>,
        processUiAction: (action: ACTION) -> Unit
    )
}