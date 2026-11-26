package com.group8.mobilefinalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.group8.mobilefinalproject.ui.VagabondApp
import com.group8.mobilefinalproject.ui.theme.VagabondTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VagabondTheme {
                VagabondApp()
            }
        }
    }
}
