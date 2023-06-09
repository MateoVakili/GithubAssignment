package com.mateo.anwbassignment.presentation.util.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun AppTextLink(
    text: String,
    url: String,
    style: TextStyle = MaterialTheme.typography.titleSmall,
) {
    val launchResourceIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val context = LocalContext.current
    Text(
        text = text,
        style = style,
        color = Color.Blue,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                try {
                    ContextCompat.startActivity(context, launchResourceIntent, null)
                } catch (e: ActivityNotFoundException) {
                    // install a browser
                }
            },
    )
}
