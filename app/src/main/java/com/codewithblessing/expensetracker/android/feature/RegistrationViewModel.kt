package com.codewithblessing.expensetracker.android.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithblessing.expensetracker.android.data.User
import com.codewithblessing.expensetracker.android.data.UserDao
import kotlinx.coroutines.launch

class RegistrationViewModel(private val userDao: UserDao) : ViewModel() {

    fun registerUser(email: String, username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val existingUser = userDao.getUserByEmailAndPassword(email,password)
            if (existingUser == null) {
                userDao.insertUser(User(email = email, username = username, password = password))
                onSuccess()
            } else {
                onError("User with this email already exists.")
            }
        }
    }
}