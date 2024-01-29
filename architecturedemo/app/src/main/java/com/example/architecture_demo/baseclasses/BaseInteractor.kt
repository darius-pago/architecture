package com.example.architecture_demo.baseclasses

import kotlinx.coroutines.flow.Flow

interface BaseInteractor<ACTION, RESULT> {

    fun actionToResult(action: ACTION): Flow<RESULT>
    fun initResults(): Flow<RESULT>
}