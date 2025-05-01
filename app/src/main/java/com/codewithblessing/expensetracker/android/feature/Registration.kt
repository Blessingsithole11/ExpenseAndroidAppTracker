package com.codewithblessing.expensetracker.android.feature

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codewithblessing.expensetracker.android.data.AppDatabase
import com.codewithblessing.expensetracker.android.data.UserDao
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.codewithblessing.expensetracker.android.R
import com.codewithblessing.expensetracker.android.ui.theme.ExpenseTrackerAndroidTheme

class Registration : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()
        val viewModelFactory = RegistrationViewModelFactory(userDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[RegistrationViewModel::class.java]

        setContent {
            ExpenseTrackerAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistrationScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Error State Variables
    var emailError by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun validateInputs(): Boolean {
        emailError = email.isBlank()
        usernameError = username.isBlank()
        passwordError = password.isBlank()
        confirmPasswordError = confirmPassword.isBlank()

        return !(emailError || usernameError || passwordError || confirmPasswordError)
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
                    text = "Register",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2F7E79)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Email Field
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

                // Username Field
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        usernameError = it.isBlank()
                    },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = usernameError,
                    supportingText = { if (usernameError) Text("Username cannot be empty", color = Color.Red) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Password Field
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

                Spacer(modifier = Modifier.height(8.dp))

                // Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmPasswordError = it.isBlank()
                    },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = confirmPasswordError,
                    supportingText = { if (confirmPasswordError) Text("Confirm Password cannot be empty", color = Color.Red) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Register Button
                Button(
                    onClick = {
                        if (validateInputs()) {
                            if (password == confirmPassword) {
                                viewModel.registerUser(
                                    email, username, password,
                                    onSuccess = { Toast.makeText(context, "Registered Successfully!", Toast.LENGTH_SHORT).show() },
                                    onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                                )
                            } else {
                                Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                            }
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
                                    listOf(Color(0xFF2F7E79), Color(0xFF0C285F))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Register",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }

                // Link to Login screen
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Already have an account? Login here",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF00C853)),
                    modifier = Modifier.clickable {
                        val intent = Intent(context, LoginScreen::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}
