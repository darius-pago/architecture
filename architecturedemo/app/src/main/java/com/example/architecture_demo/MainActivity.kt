package com.example.architecture_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.architecture_demo.ui.theme.`architecture-demo`.DemoScreen
import com.example.architecture_demo.ui.theme.`architecture-demo`.DemoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel by viewModels<DemoViewModel>()
            DemoScreen.Content(viewModel)
        }
    }
}