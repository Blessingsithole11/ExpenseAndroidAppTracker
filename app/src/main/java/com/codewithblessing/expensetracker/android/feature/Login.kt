package com.codewithblessing.expensetracker.android.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.codewithblessing.expensetracker.android.data.AppDatabase
import com.codewithblessing.expensetracker.android.ui.theme.ExpenseTrackerAndroidTheme

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Get Database and DAO instance
        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()

        // Create ViewModel using ViewModelFactory
        val viewModelFactory = LoginViewModelFactory(userDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        setContent {
            ExpenseTrackerAndroidTheme {
                LoginScreen()
            }
        }
    }
}
