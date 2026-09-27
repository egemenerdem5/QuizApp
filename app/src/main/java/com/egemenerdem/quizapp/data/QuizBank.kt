package com.egemenerdem.quizapp.data

object QuizBank {
    val questions: List<Question> = listOf(
        Question(
            id = "q1",
            prompt = "What is the capital of France?",
            options = listOf(
                AnswerOption("q1a", "Berlin"),
                AnswerOption("q1b", "Madrid"),
                AnswerOption("q1c", "Paris"),
                AnswerOption("q1d", "Rome")
            ),
            correctOptionId = "q1c"
        ),
        Question(
            id = "q2",
            prompt = "Which planet is known as the Red Planet?",
            options = listOf(
                AnswerOption("q2a", "Venus"),
                AnswerOption("q2b", "Mars"),
                AnswerOption("q2c", "Jupiter"),
                AnswerOption("q2d", "Saturn")
            ),
            correctOptionId = "q2b"
        ),
        Question(
            id = "q3",
            prompt = "In Jetpack Compose, what typically triggers recomposition?",
            options = listOf(
                AnswerOption("q3a", "Calling setContent again"),
                AnswerOption("q3b", "A change to a State object that the composable reads"),
                AnswerOption("q3c", "Every frame, regardless of state"),
                AnswerOption("q3d", "Only Activity.onResume()")
            ),
            correctOptionId = "q3b"
        ),
        Question(
            id = "q4",
            prompt = "How long is a standard marathon?",
            options = listOf(
                AnswerOption("q4a", "26.2 miles"),
                AnswerOption("q4b", "13.1 miles"),
                AnswerOption("q4c", "10 kilometers"),
                AnswerOption("q4d", "50 miles")
            ),
            correctOptionId = "q4a"
        ),
        Question(
            id = "q5",
            prompt = "Which language is used as the primary language for Android app development with Jetpack Compose?",
            options = listOf(
                AnswerOption("q5a", "Java only"),
                AnswerOption("q5b", "Swift"),
                AnswerOption("q5c", "Kotlin"),
                AnswerOption("q5d", "Dart")
            ),
            correctOptionId = "q5c"
        ),
        Question(
            id = "q6",
            prompt = "What gas do plants absorb from the air during photosynthesis?",
            options = listOf(
                AnswerOption("q6a", "Oxygen"),
                AnswerOption("q6b", "Nitrogen"),
                AnswerOption("q6c", "Hydrogen"),
                AnswerOption("q6d", "Carbon dioxide")
            ),
            correctOptionId = "q6d"
        ),
        Question(
            id = "q7",
            prompt = "Why wrap list items in a Compose key() when identity matters?",
            options = listOf(
                AnswerOption("q7a", "It makes the UI draw faster on every frame"),
                AnswerOption("q7b", "It lets Compose reuse the same instance across list changes instead of restarting state and effects"),
                AnswerOption("q7c", "It is required for every Text() call"),
                AnswerOption("q7d", "It disables recomposition entirely")
            ),
            correctOptionId = "q7b"
        ),
        Question(
            id = "q8",
            prompt = "Which ocean is the largest?",
            options = listOf(
                AnswerOption("q8a", "Atlantic Ocean"),
                AnswerOption("q8b", "Indian Ocean"),
                AnswerOption("q8c", "Pacific Ocean"),
                AnswerOption("q8d", "Arctic Ocean")
            ),
            correctOptionId = "q8c"
        )
    )
}
