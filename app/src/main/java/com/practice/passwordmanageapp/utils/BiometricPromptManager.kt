package com.practice.passwordmanageapp.utils

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class BiometricPromptManager(private val context: Context) {

    private val biometricManager = BiometricManager.from(context)

    // Check if biometric authentication is available
    fun canAuthenticate(): Boolean {
        val result = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        return result == BiometricManager.BIOMETRIC_SUCCESS
    }

    // Show the biometric authentication prompt
    fun showPrompt(
        activity: AppCompatActivity,  // Changed to AppCompatActivity or FragmentActivity
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        // Create an executor to run on the main thread
        val executor: Executor = ContextCompat.getMainExecutor(context)

        // Build the prompt information
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Authenticate to access your passwords")
            .setNegativeButtonText("Use App PIN")
            .build()

        // Initialize the BiometricPrompt with AppCompatActivity
        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()  // Call the success callback when authentication is successful
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError()  // Call the error callback when there is an error
            }
        })

        // Start authentication with the prompt
        biometricPrompt.authenticate(promptInfo)
    }
}
