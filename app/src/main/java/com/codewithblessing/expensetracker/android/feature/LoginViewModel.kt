package com.codewithblessing.expensetracker.android.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithblessing.expensetracker.android.data.UserDao
import kotlinx.coroutines.launch

class LoginViewModel(private val userDao: UserDao) : ViewModel() {

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserByEmailAndPassword(email, password)
            if (user != null) {
                onSuccess()  // Login successful
            } else {
                onError("Invalid email or password.") // Show error
            }
        }
    }
}
