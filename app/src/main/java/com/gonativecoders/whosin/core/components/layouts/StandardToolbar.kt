package com.gonativecoders.whosin.core.components.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.theme.WhosInTheme

@Composable
fun StandardToolbarLayout(
    modifier: Modifier = Modifier,
    onBackArrowPressed: (() -> Unit)? = null,
    title: String? = null,
    actions: @Composable (RowScope.() -> Unit) = { },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomCenter,
            imageVector = ImageVector.vectorResource(id = R.drawable.toolbar_background),
            contentDescription = ""
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier,
                    title = {
                        Text(
                            text = title ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        if (onBackArrowPressed != null) {
                            IconButton(onClick = onBackArrowPressed) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowBack,
                                    contentDescription = "Previous Screen",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    actions = actions
                )
            }
        ) { innerPadding ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 16.dp)
            ) {
                content()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun StandardToolbarLayoutPreview() {
    WhosInTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            StandardToolbarLayout(
                onBackArrowPressed = {},
                title = "Screen Title",
            ) {

            }
        }

    }
}