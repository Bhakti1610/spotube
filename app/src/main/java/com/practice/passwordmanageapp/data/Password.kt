package com.practice.passwordmanageapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practice.passwordmanageapp.utils.EncryptionUtils

@Entity(tableName = "password_table")
data class Password(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val account: String,
    val username: String,
    val encryptedPassword: String
) {
    fun getDecryptedPassword(): String = EncryptionUtils.decrypt(encryptedPassword)

    companion object {
        fun create(account: String, username: String, rawPassword: String): Password {
            val encryptedPassword = EncryptionUtils.encrypt(rawPassword)
            return Password(account = account, username = username, encryptedPassword = encryptedPassword)
        }
    }
}
