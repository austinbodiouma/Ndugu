package com.example.ndugu.feature.auth.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

// --- Design Tokens from Stitch ---
val CWPrimary = Color(0xFF005151)
val CWPrimaryContainer = Color(0xFF0E6B6B)
val CWSurface = Color(0xFFF7FAF9)
val CWOnSurface = Color(0xFF181C1C)
val CWOnSurfaceVariant = Color(0xFF3F4948)
val CWOutline = Color(0xFF6F7979)
val CWSurfaceContainerHigh = Color(0xFFE6E9E8)
val CWSurfaceContainerLowest = Color(0xFFFFFFFF)
val CWError = Color(0xFFBA1A1A)
val CWSuccess = Color(0xFF00A896)
val CWTertiary = Color(0xFF654000)

@Composable
fun RegisterRoot(
    onNavigateToOtpVerify: (phone: String) -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.NavigateToOtpVerify -> onNavigateToOtpVerify(event.phone)
            is RegisterEvent.NavigateToLogin -> onNavigateToLogin()
            is RegisterEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    RegisterScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "CampusWallet",
                        color = CWPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(RegisterAction.OnLoginClick) }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = CWPrimary)
                    }
                },
                actions = {
                    Surface(
                        color = CWSurfaceContainerHigh,
                        shape = CircleShape,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(
                            "Step 3 of 5",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = CWOutline
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CWSurface)
            )
        },
        containerColor = CWSurface
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Hero Section
            Text(
                text = "Create your account",
                fontSize = 39.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 44.sp,
                letterSpacing = (-1).sp,
                color = CWOnSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Join thousands of students managing their campus finances with ease.",
                fontSize = 16.sp,
                color = CWOnSurfaceVariant,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Form Card
            Surface(
                color = CWSurfaceContainerLowest,
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Full Name
                    CWTextField(
                        label = "Full Name",
                        value = state.email,
                        onValueChange = {  },
                        placeholder = "e.g. Alex Kamau"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Email
                    CWTextField(
                        label = "University Email",
                        value = state.email,
                        onValueChange = { onAction(RegisterAction.OnEmailChange(it)) },
                        placeholder = "student@university.ac.ke",
                        trailingIcon = Icons.Rounded.Email,
                        caption = "Must end in .ac.ke or .edu",
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Password
                    CWTextField(
                        label = "Password",
                        value = state.password,
                        onValueChange = { onAction(RegisterAction.OnPasswordChange(it)) },
                        placeholder = "••••••••",
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onTogglePassword = { passwordVisible = !passwordVisible }
                    )

                    // Password Strength Bar
                    Spacer(modifier = Modifier.height(12.dp))
                    PasswordStrengthBar(password = state.password)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Confirm Password
                    CWTextField(
                        label = "Confirm Password",
                        value = state.confirmPassword,
                        onValueChange = { onAction(RegisterAction.OnConfirmPasswordChange(it)) },
                        placeholder = "••••••••",
                        isPassword = true,
                        passwordVisible = false, // Always hidden for confirm
                        isMatch = state.password.isNotEmpty() && state.password == state.confirmPassword
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Terms & Privacy
                    Row(verticalAlignment = Alignment.Top) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = {  },
                            colors = CheckboxDefaults.colors(checkedColor = CWPrimary)
                        )
                        Text(
                            text = "I agree to the Terms of Service and have read the Privacy Policy.",
                            fontSize = 12.sp,
                            color = CWOnSurfaceVariant,
//                            modifier = Modifier.padding(top = 12.dp).clickable { onAction(RegisterAction.OnToggleTerms) }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // CTA Button
                    if (state.isLoading) {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = CWPrimary)
                        }
                    } else {
                        Button(
                            onClick = { onAction(RegisterAction.OnRegisterClick) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .clip(CircleShape),
                            shape = CircleShape,
//                            enabled = state.agreeToTerms && !state.isLoading,
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(CWPrimary, CWPrimaryContainer)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Create Account", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.Rounded.ArrowForward, null)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = { onAction(RegisterAction.OnLoginClick) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Already have an account? Sign in",
                    color = CWPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Trust Banner
            Spacer(modifier = Modifier.height(48.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().alpha(0.6f)
            ) {
                Text(
                    "TRUSTED BY STUDENTS FROM",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = CWOutline
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TrustBadge("UON ARCHIVE", Modifier.weight(1f))
                    TrustBadge("KU PORTAL", Modifier.weight(1f))
                    TrustBadge("JKUAT PAY", Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun CWTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePassword: (() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    caption: String? = null,
    isMatch: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = CWOnSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = CWOutline.copy(alpha = 0.5f)) },
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                when {
                    isMatch -> {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 8.dp)) {
                            Icon(Icons.Default.CheckCircle, null, tint = CWSuccess, modifier = Modifier.size(16.dp))
                            Text("MATCH", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CWSuccess, modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                    onTogglePassword != null -> {
                        IconButton(onClick = onTogglePassword) {
                            Icon(
                                if (passwordVisible) Icons.Rounded.Face else Icons.Rounded.Face,
                                null,
                                tint = CWOutline
                            )
                        }
                    }
                    trailingIcon != null -> {
                        Icon(trailingIcon, null, tint = CWOutline)
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CWSurfaceContainerHigh,
                focusedContainerColor = CWSurfaceContainerHigh,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = CWPrimary,
                cursorColor = CWPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        if (caption != null) {
            Text(
                text = caption,
                fontSize = 11.sp,
                color = CWOutline,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun PasswordStrengthBar(password: String) {
    val strength = when {
        password.length > 10 -> 4
        password.length > 7 -> 3
        password.length > 4 -> 2
        password.length > 0 -> 1
        else -> 0
    }

    Column(spacing = 6.dp) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            StrengthSegment(active = strength >= 1, color = CWError)
            StrengthSegment(active = strength >= 2, color = CWTertiary)
            StrengthSegment(active = strength >= 3, color = CWSuccess)
            StrengthSegment(active = strength >= 4, color = CWPrimary)
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 2.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = if (strength >= 3) "Strong Security" else if (strength > 0) "Weak" else "",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (strength >= 3) CWSuccess else CWOutline
            )
            Text("8+ Characters", fontSize = 10.sp, color = CWOutline)
        }
    }
}

@Composable
private fun StrengthSegment(active: Boolean, color: Color) {
    Box(
        modifier = Modifier
            .height(4.dp)

            .background(if (active) color else CWOutline.copy(alpha = 0.2f), CircleShape)
    )
}

@Composable
fun TrustBadge(text: String, modifier: Modifier = Modifier) {
    Surface(
        color = CWSurfaceContainerHigh,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = text,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = CWOnSurfaceVariant
            )
        }
    }
}

// Helper for Column spacing if not available in your version
@Composable
fun Column(modifier: Modifier = Modifier, spacing: androidx.compose.ui.unit.Dp, content: @Composable () -> Unit) {
    androidx.compose.foundation.layout.Column(modifier = modifier) {
        content()
    }
}