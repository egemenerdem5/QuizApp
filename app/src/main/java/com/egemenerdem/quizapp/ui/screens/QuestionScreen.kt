package com.egemenerdem.quizapp.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egemenerdem.quizapp.data.AnswerOption
import com.egemenerdem.quizapp.data.Question
import com.egemenerdem.quizapp.ui.theme.CorrectGreen
import com.egemenerdem.quizapp.ui.theme.IncorrectRed
import com.egemenerdem.quizapp.ui.theme.QuizAppTheme

@Composable
fun QuestionScreen(
    question: Question,
    questionNumber: Int,
    totalQuestions: Int,
    selectedOptionId: String?,
    isAnswerLocked: Boolean,
    secondsRemaining: Int,
    isLastQuestion: Boolean,
    onSelectAnswer: (String) -> Unit,
    onConfirmAnswer: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question $questionNumber of $totalQuestions",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${secondsRemaining}s",
                style = MaterialTheme.typography.titleMedium,
                color = if (secondsRemaining <= 5) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        }
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { questionNumber / totalQuestions.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = question.prompt,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier.selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            question.options.forEach { option ->
                // Same call site in a loop: key() tells Compose which option instance
                // to keep when the list order changes.
                key(option.id) {
                    AnswerOptionCard(
                        option = option,
                        selected = option.id == selectedOptionId,
                        showResult = isAnswerLocked,
                        isCorrect = option.id == question.correctOptionId,
                        enabled = !isAnswerLocked,
                        onClick = { onSelectAnswer(option.id) }
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        if (isAnswerLocked) {
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLastQuestion) "See results" else "Next question")
            }
        } else {
            Button(
                onClick = onConfirmAnswer,
                enabled = selectedOptionId != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Check answer")
            }
        }
    }
}

@Composable
private fun AnswerOptionCard(
    option: AnswerOption,
    selected: Boolean,
    showResult: Boolean,
    isCorrect: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor by animateColorAsState(
        targetValue = when {
            showResult && isCorrect -> CorrectGreen
            showResult && selected && !isCorrect -> IncorrectRed
            selected -> MaterialTheme.colorScheme.secondaryContainer
            else -> MaterialTheme.colorScheme.surfaceContainerHighest
        },
        label = "optionColor"
    )
    val contentColor by animateColorAsState(
        targetValue = when {
            showResult && isCorrect -> Color.White
            showResult && selected && !isCorrect -> Color.White
            else -> MaterialTheme.colorScheme.onSurface
        },
        label = "optionContentColor"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                enabled = enabled,
                role = Role.RadioButton,
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = option.text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = contentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestionScreenPreview() {
    QuizAppTheme(dynamicColor = false) {
        QuestionScreen(
            question = Question(
                id = "preview",
                prompt = "Which planet is known as the Red Planet?",
                options = listOf(
                    AnswerOption("a", "Venus"),
                    AnswerOption("b", "Mars"),
                    AnswerOption("c", "Jupiter"),
                    AnswerOption("d", "Saturn")
                ),
                correctOptionId = "b"
            ),
            questionNumber = 2,
            totalQuestions = 8,
            selectedOptionId = "b",
            isAnswerLocked = true,
            secondsRemaining = 14,
            isLastQuestion = false,
            onSelectAnswer = {},
            onConfirmAnswer = {},
            onNext = {}
        )
    }
}
