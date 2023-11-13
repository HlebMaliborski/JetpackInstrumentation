package com.example.mylibrary

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun HiddenText() {
    Text(
        text = "Hello Text",
        modifier = Modifier.clickable { Log.d("TextScreen", "Request API") },
        minLines = 1
    )
}

