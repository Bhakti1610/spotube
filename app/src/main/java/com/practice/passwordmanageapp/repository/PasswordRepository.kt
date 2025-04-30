package com.practice.passwordmanageapp.repository

import com.practice.passwordmanageapp.data.Password
import com.practice.passwordmanageapp.data.PasswordDao
import kotlinx.coroutines.flow.Flow

class PasswordRepository(private val dao: PasswordDao) {
    fun getAllPasswords(): Flow<List<Password>> = dao.getAllPasswords()
    suspend fun insert(password: Password) = dao.insert(password)
    suspend fun update(password: Password) = dao.update(password)
    suspend fun delete(password: Password) = dao.delete(password)
}