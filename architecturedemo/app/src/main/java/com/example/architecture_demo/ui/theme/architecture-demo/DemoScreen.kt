package com.example.architecture_demo.ui.theme.`architecture-demo`

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.architecture_demo.baseclasses.BaseScreen
import kotlinx.coroutines.flow.SharedFlow

object DemoScreen : BaseScreen<DemoState, DemoAction, DemoEffect, DemoViewModel>() {
    @Composable
    override fun Screen(
        state: DemoState,
        effect: SharedFlow<DemoEffect>,
        processUiAction: (action: DemoAction) -> Unit
    ) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            effect.collect {
                when(it) {
                    is DemoEffect.SomeToastMessage -> {
                        Toast.makeText(context, "some message", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        when(state) {
            DemoState.InitialState -> ErrorState()
            DemoState.LoadingState -> Loading(processUiAction)
            is DemoState.SuccessState -> SuccessState(
                state.title,
                state.subTitle,
                state.isChecked
            )
        }
    }

    @Composable
    fun Loading(processUiAction: (action: DemoAction) -> Unit) {
        processUiAction(DemoAction.SomeUserAction)
    }

    @Composable
    fun SuccessState(
        title: String,
        subTitle: String,
        isChecked: Boolean,
    ) {

    }

    @Composable
    fun ErrorState() {

    }
}