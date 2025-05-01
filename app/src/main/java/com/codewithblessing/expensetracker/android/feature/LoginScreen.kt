package com.codewithblessing.expensetracker.android.feature

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.codewithblessing.expensetracker.android.MainActivity
import com.codewithblessing.expensetracker.android.data.AppDatabase
import com.codewithblessing.expensetracker.android.feature.ui.theme.SpendWiseTheme

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Get database and DAO
        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()

        // Initialize ViewModel with Factory
        val viewModelFactory = LoginViewModelFactory(userDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        setContent {
            SpendWiseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        onLoginSuccess = {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish() // Finish login activity so user can't go back
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit // This function is called when login is successful
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun validateInputs(): Boolean {
        emailError = email.isBlank()
        passwordError = password.isBlank()
        return !(emailError || passwordError)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2F7E79), Color(0xFF2F7E79))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00C853)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Here we write Email Input
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = it.isBlank()
                    },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = emailError,
                    supportingText = { if (emailError) Text("Email cannot be empty", color = Color.Red) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Here we write Password Input
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.isBlank()
                    },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = passwordError,
                    supportingText = { if (passwordError) Text("Password cannot be empty", color = Color.Red) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Login Button
                Button(
                    onClick = {
                        if (validateInputs()) {
                            viewModel.loginUser(
                                email, password,
                                onSuccess = {
                                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                                    onLoginSuccess() // Navigate to MainActivity
                                },
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(Color(0xFF2F7E79), Color(0xFF2F7E79))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
                // Link to Registration screen
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Don't have an account? Register here",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF00C853)),
                    modifier = Modifier.clickable {
                        val intent = Intent(context, Registration::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}