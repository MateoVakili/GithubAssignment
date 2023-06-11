package com.mateo.anwbassignment.presentation.util.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Omitted dynamic color and dark mode.
@Composable
fun ANWBAssignmentTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Purple80,
            secondary = PurpleGrey80,
            tertiary = Pink80,
            background = white,
            onPrimaryContainer = containerBackground,
            secondaryContainer = secondaryContainer
        ),
        content = content
    )
}