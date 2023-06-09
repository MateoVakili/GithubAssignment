package com.mateo.anwbassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mateo.anwbassignment.presentation.AssignmentApp
import com.mateo.anwbassignment.presentation.util.theme.ANWBAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ANWBAssignmentTheme {
                AssignmentApp()
            }
        }
    }
}