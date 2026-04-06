package com.example.ndugu.feature.auth.presentation.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.GroupAdd
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

// Stitch-specific colors from Color.kt
private val MeshColor1 = Color(0xFFA2F0EF) // Primary Fixed
private val MeshColor2 = Color(0xFFE1DFFF) // Secondary Fixed
private val MeshColor3 = Color(0xFFFFDDB4) // Tertiary Fixed
private val MeshColor4 = Color(0xFFF7FAF9) // Surface

/**
 * WelcomeRoot — entry point for the onboarding flow.
 */
@Composable
fun WelcomeRoot(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    WelcomeScreen(
        onNavigateToLogin = onNavigateToLogin,
        onNavigateToRegister = onNavigateToRegister,
    )
}

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { WelcomeHeader() },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            HeroSection()

            Spacer(modifier = Modifier.height(32.dp))

            BrandingSection()

            Spacer(modifier = Modifier.height(48.dp))

            ValuePropositionsSection()

            Spacer(modifier = Modifier.height(48.dp))

            ActionSection(
                onJoinClick = onNavigateToRegister,
                onLoginClick = onNavigateToLogin,
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
private fun WelcomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountBalanceWallet,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "CampusWallet",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1).sp,
            ),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun HeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        // Mesh Gradient Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.4f)
                .blur(80.dp)
                .drawBehind {
                    drawCircle(
                        color = MeshColor1,
                        center = Offset(0f, 0f),
                        radius = size.minDimension / 1.5f,
                    )
                    drawCircle(
                        color = MeshColor2,
                        center = Offset(size.width, 0f),
                        radius = size.minDimension / 1.5f,
                    )
                    drawCircle(
                        color = MeshColor3,
                        center = Offset(size.width, size.height),
                        radius = size.minDimension / 1.5f,
                    )
                    drawCircle(
                        color = MeshColor4,
                        center = Offset(0f, size.height),
                        radius = size.minDimension / 1.5f,
                    )
                }
        )

        // Hero Image
        AsyncImage(
            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBm2ey_oCguELbzi0ed29n-vP9iR982llHMmNcD-2QE-dPTKRpDuCL8Y5DdzzgT5g-fSV_sqx0t2UbC1CV0fP9SNl-01JHwA_b2hZV007OqQdxbEY0PS2H57sTan8Es7TeVfW-RJvD92AxNMmTepjcC3AQ5FKaj7UzGHkEBUE-bYCzAhmjFtuQ3JXdD0snjSWXaXqunP0DJWB4etMwhnHXarveHUb4HyQig0x_35FBLlx7N3ccxC-PGe_Q9g6w1tkqrFXByBQ_sVMM",
            contentDescription = "Students on campus",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun BrandingSection() {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("Send money. Shop campus.\n")
                append("Stay in control.")
            }
        },
        style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp,
            letterSpacing = (-1).sp,
        ),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun ValuePropositionsSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ValuePropositionCard(
            title = "Top up instantly via M-Pesa",
            icon = Icons.Rounded.Payments,
            iconBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
            iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        ValuePropositionCard(
            title = "Buy & sell within your university",
            icon = Icons.Rounded.Storefront,
            iconBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        ValuePropositionCard(
            title = "Split, send, receive — all in one app",
            icon = Icons.Rounded.GroupAdd,
            iconBackgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            iconColor = MaterialTheme.colorScheme.onTertiaryContainer,
        )
    }
}

@Composable
private fun ValuePropositionCard(
    title: String,
    icon: ImageVector,
    iconBackgroundColor: Color,
    iconColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(24.dp),
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(iconBackgroundColor, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun ActionSection(
    onJoinClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Button(
            onClick = onJoinClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        ) {
            Text(
                text = "Join CampusWallet",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                ),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Log In",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.clickable { onLoginClick() },
            )
        }
    }
}
