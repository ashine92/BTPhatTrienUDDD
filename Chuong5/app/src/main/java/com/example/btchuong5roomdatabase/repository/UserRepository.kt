package com.example.btchuong5roomdatabase.repository

import androidx.lifecycle.LiveData
import com.example.btchuong5roomdatabase.data.User
import com.example.btchuong5roomdatabase.data.UserDao

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
    }
}