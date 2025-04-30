package com.practice.passwordmanageapp.utils

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object EncryptionUtils {
    // Generate a secure AES key using SecureRandom
    private val secretKey: SecretKey = generateSecretKey()

    // Generate a 128-bit AES key using SecureRandom
    private fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128, SecureRandom())  // AES key size 128-bit
        return keyGenerator.generateKey()
    }

    // Encrypt the data using AES
    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)  // Initialize cipher with the generated key
        val encrypted = cipher.doFinal(data.toByteArray())  // Encrypt the data
        return Base64.encodeToString(encrypted, Base64.DEFAULT)  // Return the encrypted data as Base64 string
    }

    // Decrypt the data using AES
    fun decrypt(data: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)  // Initialize cipher with the same key
        val decoded = Base64.decode(data, Base64.DEFAULT)  // Decode the Base64 encoded data
        return String(cipher.doFinal(decoded))  // Return the decrypted string
    }
}
