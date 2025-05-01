package com.codewithblessing.expensetracker.android.feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codewithblessing.expensetracker.android.R

@Composable
fun QuizScreen(navController: NavController) {
    QuizGame(onBack = { navController.popBackStack() })
}

@Composable
fun QuizGame(onBack: () -> Unit) {
    val questions = getSampleQuestions()
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }

    fun playAgain() {
        currentQuestionIndex = 0
        score = 0
        gameOver = false
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF2F7E79))) {
        // Top bar with back button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onBack() },
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                text = "Quiz",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (gameOver) {
            QuizResult(score, questions.size, playAgain = { playAgain() })
        } else {
            val question = questions[currentQuestionIndex]
            QuizQuestionScreen(
                question = question,
                onAnswerSelected = { selectedAnswer ->
                    if (selectedAnswer == question.correctAnswer) score++
                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                    } else {
                        gameOver = true
                    }
                }
            )
        }
    }
}

@Composable
fun QuizQuestionScreen(question: QuizQuestion, onAnswerSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2F7E79)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question.question,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            question.options.forEach { answer ->
                Button(
                    onClick = { onAnswerSelected(answer) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = answer, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun QuizResult(score: Int, total: Int, playAgain: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2F7E79)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Quiz Over!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Your Score: $score / $total",
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { playAgain() }) {
                Text(text = "Play Again")
            }
        }
    }
}