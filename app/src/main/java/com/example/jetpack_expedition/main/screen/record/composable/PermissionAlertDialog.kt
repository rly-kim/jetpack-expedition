package com.example.jetpack_expedition.main.screen.record.composable

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.jetpack_expedition.R

@Composable
fun PermissionAlertDialog(context: Context, onIgnored: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        text = {
            Text(stringResource(R.string.permissionGuideBodyText))
        },
        buttons = { AlertButtons(context) { onIgnored()} },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
        )
    )
}

@Composable
fun AlertButtons(context: Context, permissionIgnore: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(onClick = {
            permissionIgnore()
        }) {
            Text(stringResource(R.string.permissionIgnoreText))
        }
        Button(onClick = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", "com.example.jetpack_expedition", null)
            intent.data = uri

            // Add settings key to navigate to a specific setting page
            intent.putExtra(
                Settings.EXTRA_APP_PACKAGE,
                "com.example.jetpack_expedition"
            )
            intent.putExtra(
                Settings.EXTRA_CHANNEL_ID,
                "channelId"
            ) // Replace with your setting key
            context.startActivity(intent)
        }) {
            Text(stringResource(R.string.permissionSettingText))
        }
    }
}
