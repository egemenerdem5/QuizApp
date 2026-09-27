package com.egemenerdem.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egemenerdem.quizapp.ui.theme.QuizAppTheme

@Composable
fun ResultsScreen(
    score: Int,
    totalQuestions: Int,
    onPlayAgain: () -> Unit,
    onReturnToWelcome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val message = when {
        score == totalQuestions -> "Perfect score"
        score >= totalQuestions * 0.7 -> "Great work"
        score >= totalQuestions * 0.4 -> "Nice try"
        else -> "Keep practicing"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "$score / $totalQuestions correct",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onPlayAgain,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Play again")
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(
            onClick = onReturnToWelcome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to start")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultsScreenPreview() {
    QuizAppTheme(dynamicColor = false) {
        ResultsScreen(
            score = 6,
            totalQuestions = 8,
            onPlayAgain = {},
            onReturnToWelcome = {}
        )
    }
}
