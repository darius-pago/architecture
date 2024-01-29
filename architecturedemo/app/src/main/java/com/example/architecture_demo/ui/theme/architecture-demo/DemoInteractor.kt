package com.example.architecture_demo.ui.theme.`architecture-demo`

import com.example.architecture_demo.baseclasses.BaseInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class DemoInteractor: BaseInteractor<DemoAction, DemoResult> {

    val someExpensiveRepositoryOperation = flow<String> {  }

    override fun initResults(): Flow<DemoResult> = flowOf(DemoResult.LoadingResult)

    override fun actionToResult(action: DemoAction): Flow<DemoResult> {
        return when(action) {
            is DemoAction.SomeUserAction -> {
                flow {
                    emit(DemoResult.LoadingResult)

                    delay(2000)

                    someExpensiveRepositoryOperation.collect {
                        emit(DemoResult.SomeResult(title = it))
                    }
                }
            }
        }
    }
}