package com.gonativecoders.whosin.core.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun BaseTextField(label: String, value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier, icon: TextFieldIcon? = null, onNext: FocusDirection, keyboardOptions: KeyboardOptions) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(onNext) }),
        onValueChange = { onNewValue(it) },
        placeholder = { Text(label) },
        leadingIcon = { icon?.let { Icon(imageVector = icon.icon, contentDescription = icon.description) } }
    )
}

data class TextFieldIcon(
    val icon: ImageVector,
    val description: String
)