@file:OptIn(ExperimentalLayoutApi::class)

package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.data.whosin.model.User
import com.gonativecoders.whosin.data.whosin.model.Week
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import com.gonativecoders.whosin.ui.theme.WhosInTheme
import org.koin.androidx.compose.getViewModel
import java.util.*


val week = Week(
    Date(), listOf(
        WorkDay(
            "Mon", 1, listOf(
                User(1, "Vin", R.drawable.man)
            )
        ),
        WorkDay(
            "Tue", 2, listOf(
                User(1, "Vin", R.drawable.man),
                User(2, "Maria", R.drawable.woman),
                User(3, "Kasha", R.drawable.user),
                User(4, "Dave", R.drawable.gamer),
                User(1, "Vin", R.drawable.man),
                User(2, "Maria", R.drawable.woman),
                User(3, "Kasha", R.drawable.user),
                User(4, "Dave", R.drawable.gamer),
                User(1, "Vin", R.drawable.man),
                User(2, "Maria", R.drawable.woman),
                User(3, "Kasha", R.drawable.user),
                User(4, "Dave", R.drawable.gamer),
                User(1, "Vin", R.drawable.man),
                User(2, "Maria", R.drawable.woman),
                User(3, "Kasha", R.drawable.user),
                User(4, "Dave", R.drawable.gamer),
            )
        ),
        WorkDay(
            "Wed", 3, listOf(
                User(2, "Maria", R.drawable.woman),
                User(1, "Vin", R.drawable.man),
                User(4, "Dave", R.drawable.gamer),
            )
        ),
        WorkDay(
            "Thu", 4, listOf(
                User(3, "Kasha", R.drawable.user),
                User(4, "Dave", R.drawable.gamer),
            )
        ),
        WorkDay(
            "Fri", 5, listOf(
                User(1, "Vin", R.drawable.man),
                User(2, "Maria", R.drawable.woman),
                User(3, "Kasha", R.drawable.user),
                User(4, "Dave", R.drawable.gamer),
            )
        )
    )
)

@Composable
fun WhosInScreen(
    viewModel: WhosInViewModel = getViewModel()
) {

    WhosInContent(week)

}

@Composable
fun WhosInContent(
    week: Week
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            ) {
            Spacer(modifier = Modifier.width(8.dp))
            week.days.forEach { day ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = CenterHorizontally
                ) {
                    DayHeader(day = day.day)
                    Spacer(modifier = Modifier.height(24.dp))
                    AvatarList(day.attendees)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }


        }


    }
}


@Composable
private fun AvatarList(people: List<User>) {
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
fun Avatar(person: User) {
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
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = person.nickName)

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
            WhosInContent(week)
        }

    }
}