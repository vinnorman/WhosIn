package com.gonativecoders.whosin.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithBackArrow(
    onBackArrowPressed: () -> Unit,
    title: String? = null,
    actions: @Composable (RowScope.() -> Unit) = { },
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackArrowPressed) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Account Button"
                    )
                }
            },
            title = {
                if (title != null) Text(text = title, style = MaterialTheme.typography.titleMedium)
            },
            actions = actions
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}