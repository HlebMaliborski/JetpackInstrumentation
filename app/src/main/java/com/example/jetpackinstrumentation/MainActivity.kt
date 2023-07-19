package com.example.jetpackinstrumentation

import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackinstrumentation.ui.theme.JetpackInstrumentationTheme
import com.google.android.material.slider.RangeSlider

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        test()
        setContent {
            //Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", modifier = Modifier.clickable {
        Log.d("MainActivityText", "Click")
    })
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun Slide() {
    var sliderPosition by remember { mutableStateOf(0f..100f) }
    Text(text = sliderPosition.toString())
    RangeSlider(
        modifier = Modifier.semantics { contentDescription = "dsavv" },
        steps = 5,
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = 0f..100f,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colors.secondary,
            activeTrackColor = MaterialTheme.colors.secondary
        )
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun Slide1() {
    var sliderPosition by remember { mutableStateOf(0f) }
    Text(text = sliderPosition.toString())
    Slider(
        modifier = Modifier.semantics(mergeDescendants = true) { contentDescription = "dsada" },
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
        },
        onValueChangeFinished = {},
        valueRange = 0f..100f,
        steps = 5,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colors.secondary,
            activeTrackColor = MaterialTheme.colors.secondary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackInstrumentationTheme {
        Greeting("Android")
    }
}
