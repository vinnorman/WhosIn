package com.gonativecoders.whosin.core.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.team.model.Member

@Composable
fun UserAvatar(member: Member) {
        AsyncImage(
            model = member.photoUri,
            contentDescription = "User Profile Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WhosInTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            UserAvatar(member = Member(displayName = "Vin"))
        }
    }
}