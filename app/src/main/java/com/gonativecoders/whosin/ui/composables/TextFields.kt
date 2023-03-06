@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.ui.screens.onboarding.jointeam.TeamCodeField

@Composable
fun EmailField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithIcon(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    icon: ImageVector,
    contentDescription: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words)
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        keyboardOptions = keyboardOptions,
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = contentDescription) }
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
            Spacer(modifier = Modifier.height(12.dp))
            TeamCodeField(value = "", onNewValue = {})
        }
    }
}