package com.example.ndugu.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ndugu.core.designsystem.theme.CWPremiumOutlineVariant
import com.example.ndugu.core.designsystem.theme.CWPremiumTeal

@Composable
fun OtpInputField(
    otpCode: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    digitCount: Int = 6
) {
    BasicTextField(
        value = otpCode,
        onValueChange = {
            if (it.length <= digitCount && it.all { char -> char.isDigit() }) {
                onOtpChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        modifier = modifier,
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(digitCount) { index ->
                    val char = when {
                        index < otpCode.length -> otpCode[index].toString()
                        else -> ""
                    }
                    val isFocused = otpCode.length == index

                    OtpDigitBox(
                        char = char,
                        isFocused = isFocused
                    )
                }
            }
        }
    )
}

@Composable
private fun OtpDigitBox(
    char: String,
    isFocused: Boolean
) {
    Box(
        modifier = Modifier
            .width(48.dp)
            .height(56.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = CWPremiumTeal.copy(alpha = 0.08f),
                spotColor = CWPremiumTeal.copy(alpha = 0.08f)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused) CWPremiumTeal else CWPremiumOutlineVariant,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (char.isNotEmpty()) CWPremiumTeal else CWPremiumOutlineVariant.copy(alpha = 0.5f)
            )
        )
        if (char.isEmpty() && !isFocused) {
            Text(
                text = "0",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = CWPremiumOutlineVariant.copy(alpha = 0.3f)
                )
            )
        }
    }
}
