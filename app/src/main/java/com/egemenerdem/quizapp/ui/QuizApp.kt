package com.egemenerdem.quizapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.egemenerdem.quizapp.ui.screens.QuestionScreen
import com.egemenerdem.quizapp.ui.screens.ResultsScreen
import com.egemenerdem.quizapp.ui.screens.WelcomeScreen
import kotlinx.coroutines.delay

@Composable
fun QuizApp(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        QuizNavHost(
            uiState = uiState,
            onStart = viewModel::startQuiz,
            onSelectAnswer = viewModel::selectAnswer,
            onConfirmAnswer = viewModel::confirmAnswer,
            onNext = viewModel::goToNext,
            onTimerTick = viewModel::onTimerTick,
            onPlayAgain = viewModel::playAgain,
            onReturnToWelcome = viewModel::returnToWelcome,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Each phase is a different call site, so Welcome / Question / Results each have
 * their own Composition identity. When the phase changes, the previous screen
 * leaves Composition and the next one enters.
 */
@Composable
private fun QuizNavHost(
    uiState: QuizUiState,
    onStart: () -> Unit,
    onSelectAnswer: (String) -> Unit,
    onConfirmAnswer: () -> Unit,
    onNext: () -> Unit,
    onTimerTick: () -> Unit,
    onPlayAgain: () -> Unit,
    onReturnToWelcome: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState.phase) {
        QuizPhase.Welcome -> {
            WelcomeScreen(
                questionCount = uiState.totalQuestions,
                secondsPerQuestion = QuizUiState.QUESTION_SECONDS,
                onStart = onStart,
                modifier = modifier
            )
        }

        QuizPhase.InProgress -> {
            val question = uiState.currentQuestion ?: return
            // key(question.id) preserves this subtree's identity with the question,
            // so the timer effect is cancelled and restarted when the question changes.
            key(question.id) {
                QuestionTimer(
                    questionId = question.id,
                    isLocked = uiState.isAnswerLocked,
                    onTick = onTimerTick
                )
                QuestionScreen(
                    question = question,
                    questionNumber = uiState.questionNumber,
                    totalQuestions = uiState.totalQuestions,
                    selectedOptionId = uiState.selectedOptionId,
                    isAnswerLocked = uiState.isAnswerLocked,
                    secondsRemaining = uiState.secondsRemaining,
                    isLastQuestion = uiState.isLastQuestion,
                    onSelectAnswer = onSelectAnswer,
                    onConfirmAnswer = onConfirmAnswer,
                    onNext = onNext,
                    modifier = modifier
                )
            }
        }

        QuizPhase.Results -> {
            ResultsScreen(
                score = uiState.score,
                totalQuestions = uiState.totalQuestions,
                onPlayAgain = onPlayAgain,
                onReturnToWelcome = onReturnToWelcome,
                modifier = modifier
            )
        }
    }
}

/**
 * Side effects must be tied to Composition identity. This LaunchedEffect restarts
 * only when [questionId] or [isLocked] changes — not on every recomposition.
 */
@Composable
private fun QuestionTimer(
    questionId: String,
    isLocked: Boolean,
    onTick: () -> Unit
) {
    LaunchedEffect(questionId, isLocked) {
        if (isLocked) return@LaunchedEffect
        while (true) {
            delay(1_000)
            onTick()
        }
    }
}
