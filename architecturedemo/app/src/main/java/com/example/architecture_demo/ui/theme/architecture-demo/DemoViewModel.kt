package com.example.architecture_demo.ui.theme.`architecture-demo`

import com.example.architecture_demo.baseclasses.BaseViewModel

class DemoViewModel: BaseViewModel<DemoState, DemoAction, DemoEffect, DemoResult>(
    DemoInteractor(),
    DemoState.InitialState
) {
    override suspend fun handleResult(previousState: DemoState, result: DemoResult): DemoState {
        return when(result) {
            is DemoResult.LoadingResult -> {
                DemoState.LoadingState
            }

            is DemoResult.SomeResult -> {
                emmitEffect(DemoEffect.SomeToastMessage)

                DemoState.SuccessState(
                    title = result.title,
                    subTitle = result.title,
                    isChecked = false
                )
            }
        }
    }
}

sealed interface DemoState {
    data object InitialState : DemoState
    data object LoadingState : DemoState
    class SuccessState(
        val title: String,
        val subTitle: String,
        val isChecked: Boolean,
    ) : DemoState
}

sealed interface DemoAction {
    data object SomeUserAction : DemoAction
}

sealed interface DemoResult {
    data object LoadingResult : DemoResult
    data class SomeResult(val title: String) : DemoResult
}

sealed interface DemoEffect {
    data object SomeToastMessage : DemoEffect
}