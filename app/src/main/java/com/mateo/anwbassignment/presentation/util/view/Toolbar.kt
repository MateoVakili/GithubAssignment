package com.mateo.anwbassignment.presentation.util.view

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mateo.anwbassignment.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        actions = actions,
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    )
}


@Preview
@Composable
fun ToolbarPreview() {
    Toolbar(
        title = stringResource(id = R.string.app_name),
        actions = {
            IconButton({}) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                )
            }
        }
    )
}