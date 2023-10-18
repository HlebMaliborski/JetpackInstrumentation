package com.example.jetpackinstrumentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.example.jetpackinstrumentation.ui.theme.JetpackInstrumentationTheme

public class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackInstrumentationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Row {
                        Button(onClick = { }) {
                            Text(text = "Click button!")
                        }

                        Text(text = "Click Text", modifier = Modifier.clickable { })
                    }
                }
            }
        }
    }
}
