package com.egemenerdem.quizapp.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.egemenerdem.quizapp.data.Question

enum class QuizPhase {
    Welcome,
    InProgress,
    Results
}

/**
 * Stable UI state so Compose can skip recomposition when values are unchanged.
 * See: https://developer.android.com/develop/ui/compose/lifecycle
 */
@Stable
@Immutable
data class QuizUiState(
    val phase: QuizPhase = QuizPhase.Welcome,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val selectedOptionId: String? = null,
    val isAnswerLocked: Boolean = false,
    val secondsRemaining: Int = QUESTION_SECONDS
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    val questionNumber: Int
        get() = currentIndex + 1

    val totalQuestions: Int
        get() = questions.size

    val isLastQuestion: Boolean
        get() = currentIndex >= questions.lastIndex

    companion object {
        const val QUESTION_SECONDS = 20
    }
}
