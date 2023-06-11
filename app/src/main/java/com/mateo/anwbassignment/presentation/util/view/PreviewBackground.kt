package com.mateo.anwbassignment.presentation.util.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.mateo.anwbassignment.presentation.util.theme.ANWBAssignmentTheme

@Composable
fun PreviewBackground(content: @Composable () -> Unit) {
    ANWBAssignmentTheme {
        Column {
            content()
        }
    }
}