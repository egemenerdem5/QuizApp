package com.egemenerdem.quizapp

import com.egemenerdem.quizapp.ui.QuizPhase
import com.egemenerdem.quizapp.ui.QuizViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizViewModelTest {

    @Test
    fun confirmAnswer_incrementsScoreWhenCorrect() {
        val viewModel = QuizViewModel()
        viewModel.startQuiz()
        val question = viewModel.uiState.value.currentQuestion!!

        viewModel.selectAnswer(question.correctOptionId)
        viewModel.confirmAnswer()

        val state = viewModel.uiState.value
        assertTrue(state.isAnswerLocked)
        assertEquals(1, state.score)
    }

    @Test
    fun goToNext_onLastQuestion_showsResults() {
        val viewModel = QuizViewModel()
        viewModel.startQuiz()
        val total = viewModel.uiState.value.totalQuestions

        repeat(total) {
            val question = viewModel.uiState.value.currentQuestion!!
            viewModel.selectAnswer(question.options.first().id)
            viewModel.confirmAnswer()
            viewModel.goToNext()
        }

        assertEquals(QuizPhase.Results, viewModel.uiState.value.phase)
    }
}
