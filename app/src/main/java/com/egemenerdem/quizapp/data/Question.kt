package com.egemenerdem.quizapp.data

/**
 * Immutable quiz models. Compose treats data classes of stable types as stable,
 * so QuestionScreen can skip recomposition when the same question instance is passed.
 */
data class AnswerOption(
    val id: String,
    val text: String
)

data class Question(
    val id: String,
    val prompt: String,
    val options: List<AnswerOption>,
    val correctOptionId: String
)
