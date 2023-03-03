@file:OptIn(ExperimentalLayoutApi::class)

package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.ui.theme.WhosInTheme

@Composable
fun WhosInScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {


        val data = mapOf(
            "Mon" to listOf(
                AvatarData("Vin", R.drawable.man, true),
            ),
            "Tue" to listOf(
                AvatarData("Vin", R.drawable.man, true),
                AvatarData("Maria", R.drawable.woman, true),
                AvatarData("Kasha", R.drawable.user, true),
                AvatarData("Dave", R.drawable.gamer, false),
            ),
            "Wed" to listOf(
                AvatarData("Maria", R.drawable.woman, true),
                AvatarData("Vin", R.drawable.man, false),
                AvatarData("Dave", R.drawable.gamer, false),
            ),
            "Thu" to listOf(
                AvatarData("Kasha", R.drawable.user, true),
                AvatarData("Dave", R.drawable.gamer, false),
            ),
            "Fri" to listOf(
                AvatarData("Vin", R.drawable.man, true),
                AvatarData("Maria", R.drawable.woman, true),
                AvatarData("Kasha", R.drawable.user, true),
                AvatarData("Dave", R.drawable.gamer, true),
            ),
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),

            ) {
            Spacer(modifier = Modifier.width(8.dp))
            data.keys.toList().forEach { day ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = CenterHorizontally
                ) {
                    DayHeader(day = day)
                    Spacer(modifier = Modifier.height(24.dp))
                    AvatarList(data[day]!!)
                }

                Spacer(modifier = Modifier.width(8.dp))

            }

        }


    }
}

data class AvatarData(
    val name: String,
    val avatar: Int,
    val isDefinite: Boolean
)

@Composable
private fun AvatarList(people: List<AvatarData>) {
    LazyColumn(
        horizontalAlignment = CenterHorizontally
    ) {

        people.forEach { person ->
            item {
                Avatar(person)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }



    }
}


@Composable
fun DayHeader(day: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = day, textAlign = TextAlign.Center)
        }
    }
}


@Composable
fun Avatar(person: AvatarData) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(
                painter = painterResource(person.avatar),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Image(painter = painterResource(id = if (person.isDefinite) R.drawable.checked else R.drawable.question), contentDescription = "hi",
                modifier = Modifier
                    .offset(0.dp, 8.dp)
                    .shadow(2.dp, RoundedCornerShape(40))
                    .size(24.dp)
                    .align(Alignment.BottomEnd),)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = person.name)

    }
}

@Preview(showBackground = true)
@Composable
fun TeamScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            WhosInScreen()
        }

    }
}