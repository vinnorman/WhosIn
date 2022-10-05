package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.ui.theme.WhosInTheme

@Composable
fun WhosInScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {

        DayRow(day = "Monday")
        DayRow(day = "Tuesday")
        DayRow(day = "Wednesday")
        DayRow(day = "Thursday")
        DayRow(day = "Friday")


    }
}

@Composable
fun DayRow(day: String) {
    Column {
        OutlinedCard(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(day)
            }
        }
        Row {
            Text(text = "Vin")
            Text(text = "Dave")
            Text(text = "Maria")
            Text(text = "Kasha")
        }
    }
    Spacer(modifier = Modifier.height(24.dp))

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            WhosInScreen()
        }

    }
}