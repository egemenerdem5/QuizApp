package com.egemenerdem.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.egemenerdem.quizapp.ui.QuizApp
import com.egemenerdem.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                QuizApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}


