package com.practice.passwordmanageapp.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PasswordStrengthMeter(password: String) {
    val strength = remember(password) { calculatePasswordStrength(password) }

    val color = when (strength) {
        in 0..2 -> Color.Red
        in 3..4 -> Color.Yellow
        else -> Color.Green
    }

    val label = when (strength) {
        in 0..2 -> "Weak"
        in 3..4 -> "Medium"
        else -> "Strong"
    }

    Column {
        LinearProgressIndicator(progress = strength / 6f, color = color, modifier = Modifier.fillMaxWidth())
        Text(text = "Strength: $label", color = color)
    }
}

fun calculatePasswordStrength(password: String): Int {
    var score = 0
    if (password.length >= 8) score++
    if (password.any { it.isDigit() }) score++
    if (password.any { it.isUpperCase() }) score++
    if (password.any { it.isLowerCase() }) score++
    if (password.any { !it.isLetterOrDigit() }) score++
    if (password.length >= 12) score++
    return score
}
