package com.egemenerdem.quizapp.ui

import androidx.lifecycle.ViewModel
import com.egemenerdem.quizapp.data.QuizBank
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState(questions = QuizBank.questions))
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun startQuiz() {
        _uiState.value = QuizUiState(
            phase = QuizPhase.InProgress,
            questions = QuizBank.questions
        )
    }

    fun selectAnswer(optionId: String) {
        val state = _uiState.value
        if (state.phase != QuizPhase.InProgress || state.isAnswerLocked) return
        _uiState.update { it.copy(selectedOptionId = optionId) }
    }

    fun confirmAnswer() {
        val state = _uiState.value
        val question = state.currentQuestion ?: return
        val selected = state.selectedOptionId ?: return
        if (state.isAnswerLocked) return

        val earned = if (selected == question.correctOptionId) 1 else 0
        _uiState.update {
            it.copy(
                isAnswerLocked = true,
                score = it.score + earned
            )
        }
    }

    fun onTimerTick() {
        val state = _uiState.value
        if (state.phase != QuizPhase.InProgress || state.isAnswerLocked) return
        if (state.secondsRemaining <= 1) {
            lockOnTimeout()
        } else {
            _uiState.update { it.copy(secondsRemaining = it.secondsRemaining - 1) }
        }
    }

    private fun lockOnTimeout() {
        _uiState.update { state ->
            if (state.isAnswerLocked) return@update state
            val question = state.currentQuestion
            val earned =
                if (question != null && state.selectedOptionId == question.correctOptionId) 1 else 0
            state.copy(
                isAnswerLocked = true,
                secondsRemaining = 0,
                score = state.score + earned
            )
        }
    }

    fun goToNext() {
        val state = _uiState.value
        if (!state.isAnswerLocked) return
        if (state.isLastQuestion) {
            _uiState.update { it.copy(phase = QuizPhase.Results) }
        } else {
            _uiState.update {
                it.copy(
                    currentIndex = it.currentIndex + 1,
                    selectedOptionId = null,
                    isAnswerLocked = false,
                    secondsRemaining = QuizUiState.QUESTION_SECONDS
                )
            }
        }
    }

    fun playAgain() {
        startQuiz()
    }

    fun returnToWelcome() {
        _uiState.value = QuizUiState(questions = QuizBank.questions)
    }
}
