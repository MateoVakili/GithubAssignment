package com.mateo.anwbassignment.presentation.util.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.domain.github.model.SortingOptions

@Composable
fun SettingsDialog(
    currentSortOption: SortingOptions,
    onSortOptionAction: (SortingOptions) -> Unit,
    onDismissAction: () -> Unit,
) {
    Settings(
        onDismissAction = onDismissAction,
        onSortOptionAction = onSortOptionAction,
        currentSortOption = currentSortOption
    )
}

@Composable
fun Settings(
    currentSortOption: SortingOptions,
    onSortOptionAction: (SortingOptions) -> Unit,
    onDismissAction: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    AlertDialog(
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismissAction() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        title = {
            Text(
                text = stringResource(id = R.string.settings_title),
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Divider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = stringResource(id = R.string.setting_sorting_title),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                )
                Column(Modifier.selectableGroup()) {
                    SettingsDialogChooserRow(
                        text = SortingOptions.STARS.value,
                        selected = currentSortOption == SortingOptions.STARS,
                        onClick = { onSortOptionAction(SortingOptions.STARS) },
                    )
                    SettingsDialogChooserRow(
                        text = SortingOptions.UPDATED.value,
                        selected = currentSortOption == SortingOptions.UPDATED,
                        onClick = { onSortOptionAction(SortingOptions.UPDATED) },
                    )
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(id = R.string.setting_button_confirm),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .clickable { onDismissAction() }
                    .padding(horizontal = 8.dp),
            )
        },
    )
}

@Composable
fun SettingsDialogChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text, color = Color.Black)
    }
}


@Preview
@Composable
fun SettingsPreview() {
    SettingsDialog(
        SortingOptions.UPDATED, {}, {}
    )
}