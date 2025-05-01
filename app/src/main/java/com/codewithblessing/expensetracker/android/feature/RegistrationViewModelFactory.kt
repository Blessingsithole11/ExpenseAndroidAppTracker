package com.codewithblessing.expensetracker.android.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codewithblessing.expensetracker.android.data.UserDao

class RegistrationViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistrationViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}