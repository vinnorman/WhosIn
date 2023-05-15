package com.gonativecoders.whosin.core.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorView(message: String?) {
    Text(text = message ?: "Oh no!")
}