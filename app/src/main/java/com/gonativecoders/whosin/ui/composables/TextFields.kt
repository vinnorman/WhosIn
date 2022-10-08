@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R

@Composable
fun EmailField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        onValueChange = { onNewValue(it) },
        placeholder = { Text("Email") },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
    )
}


@Composable
fun TeamNameField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
        placeholder = { Text("Team Name") },
        leadingIcon = { Icon(imageVector = Icons.Filled.Group, contentDescription = "Full Name") }
    )

}

@Composable
fun NameField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
        placeholder = { Text("Full Name") },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Full Name") }
    )
}

@Composable
fun PasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(R.string.password_field_placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible.value = !isVisible.value }) {
                Icon(
                    imageVector = if (isVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Visibility"
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            EmailField(value = "", onNewValue = { })
            Spacer(modifier = Modifier.height(12.dp))
            PasswordField(value = "", onNewValue = {})
            Spacer(modifier = Modifier.height(12.dp))
            TeamNameField(value = "", onNewValue = {})
        }
    }
}