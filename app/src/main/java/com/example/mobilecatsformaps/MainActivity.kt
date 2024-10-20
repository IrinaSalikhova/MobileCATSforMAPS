package com.example.mobilecatsformaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mobilecatsformaps.ui.theme.Celadon
import com.example.mobilecatsformaps.ui.theme.MobileCATSforMAPSTheme
import com.example.mobilecatsformaps.ui.theme.myFontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileCATSforMAPSTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        fontSize = 24.sp

    )
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = Celadon,
        fontFamily = myFontFamily,
        fontSize = 24.sp

    )}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileCATSforMAPSTheme {
        Greeting("Android")
    }
}