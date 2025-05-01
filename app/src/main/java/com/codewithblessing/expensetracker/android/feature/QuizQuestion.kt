package com.codewithblessing.expensetracker.android.feature

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

fun getSampleQuestions(): List<QuizQuestion> {
    return listOf(
        QuizQuestion(
            "What is the 50/30/20 budgeting rule?",
            listOf("Needs 50%, Wants 30%, Savings 20%", "Needs 30%, Wants 50%, Savings 20%", "Needs 20%, Wants 50%, Savings 30%"),
            "Needs 50%, Wants 30%, Savings 20%"
        ),
        QuizQuestion(
            "What is an emergency fund?",
            listOf("Money for vacations", "Savings for unexpected expenses", "Extra money for fun"),
            "Savings for unexpected expenses"
        ),
        QuizQuestion(
            "What is considered bad debt?",
            listOf("Mortgage", "Credit card debt with high interest", "Student loan"),
            "Credit card debt with high interest"
        )
    )
}
