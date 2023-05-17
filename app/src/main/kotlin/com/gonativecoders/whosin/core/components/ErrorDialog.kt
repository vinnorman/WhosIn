@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.core.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gonativecoders.whosin.core.auth.exceptions.AuthException

@Composable
fun ErrorDialog(
    exception: Exception,
    onDismissed: () -> Unit
) {

    val text = when (exception) {
        is AuthException -> "Those credentials don't seem right. Please try again."
        is IllegalArgumentException -> "Please provide all the required details"
        else -> exception.message ?: "Something went wrong there. Please try again"
    }


    AlertDialog(
        onDismissRequest = onDismissed,
        title = {
            Text(text = "Whoops")
        },
        text = {
            Text(text = text)
        },
        confirmButton = {
            TextButton(
                onClick = onDismissed
            ) {
                Text("Ok")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ErrorDialog(
                exception = AuthException("some code", Exception("A firebase auth exception")),
                onDismissed = {}
            )
        }
    }
}