package com.gonativecoders.whosin.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InitialsCircle(name: String, color: String) {
    val initials = getInitials(name)
    Text(
        modifier = Modifier
            .padding(16.dp)
            .requiredWidth(30.dp)
            .drawBehind {
                drawCircle(
                    radius = 24.dp.toPx(),
                    color = Color(color.toULong()),
                )
            },
        text = initials,
        textAlign = TextAlign.Center,
        color = Color.White
    )
}

private fun getInitials(name: String): String {
    val names = name.trim().split(" ")
    return if (names.size == 1) "${names.first().first()}" else "${names.first().first()}${names.lastOrNull()?.first()}"
}