@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.gonativecoders.whosin.ui.theme.WhosInTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhosInTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginForm()
                }
            }
        }
    }
}

@Composable
fun LoginForm() {
    Column {
        OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Username")})
        OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Password")})
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WhosInTheme {
        LoginForm()
    }
}