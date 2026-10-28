package com.example.devices


import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import  com.example.devices.navigation.AppNavigation
import  com.example.devices.ui.theme.DevicesTheme


class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DevicesTheme {
                AppNavigation()
            }
        }
    }
}