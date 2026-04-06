package com.example.ndugu.feature.auth.presentation.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Splash-specific colors matching the Stitch design
private val SplashGradientStart = Color(0xFF0E6B6B)   // Primary Container
private val SplashGradientMid = Color(0xFF2D2D8F)     // Midpoint between teal and indigo
private val SplashGradientEnd = Color(0xFF5153B4)      // Secondary (Indigo)
private val SplashAccentAmber = Color(0xFFF5A623)      // Loading arc amber accent

/**
 * SplashRoot — entry point for the splash screen composable.
 * Displays a branded splash for a set duration, then navigates to the next destination.
 */
@Composable
fun SplashRoot(
    onSplashFinished: () -> Unit,
) {
    // Auto-navigate after a delay
    LaunchedEffect(Unit) {
        delay(2500L)
        onSplashFinished()
    }

    SplashScreen()
}

@Composable
fun SplashScreen() {
    // --- Entrance animation ---
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val contentAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
    )

    // --- Infinite rotation for the loading arc ---
    val infiniteTransition = rememberInfiniteTransition()
    val arcRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(SplashGradientStart, SplashGradientMid, SplashGradientEnd),
                    start = Offset.Zero,
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        // --- Decorative mesh blobs ---
        // Top-right blob
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = 120.dp, y = (-160).dp)
                .alpha(0.15f)
                .blur(100.dp)
                .align(Alignment.TopEnd)
                .drawBehind {
                    drawCircle(color = SplashGradientStart)
                }
        )
        // Bottom-left blob
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-120).dp, y = 160.dp)
                .alpha(0.2f)
                .blur(100.dp)
                .align(Alignment.BottomStart)
                .drawBehind {
                    drawCircle(color = SplashGradientEnd)
                }
        )

        // --- Central brand cluster ---
        Column(
            modifier = Modifier
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Logo mark: CW monogram in a white rounded container
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .shadow(
                        elevation = 24.dp,
                        shape = RoundedCornerShape(32.dp),
                        ambientColor = Color.Black.copy(alpha = 0.3f),
                        spotColor = Color.Black.copy(alpha = 0.3f),
                    )
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = "C",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SplashGradientStart,
                        letterSpacing = (-3).sp,
                    )
                    Text(
                        text = "W",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SplashGradientEnd,
                        letterSpacing = (-3).sp,
                        modifier = Modifier.offset(x = (-6).dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Brand identity text
            Text(
                text = "CampusWallet",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-1).sp,
                ),
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your campus. Your wallet.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                ),
                color = Color.White.copy(alpha = 0.7f),
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Loading arc (amber spinner)
            Canvas(
                modifier = Modifier.size(24.dp),
            ) {
                rotate(degrees = arcRotation) {
                    drawArc(
                        color = SplashAccentAmber,
                        startAngle = 0f,
                        sweepAngle = 270f,
                        useCenter = false,
                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round),
                    )
                }
            }
        }

        // --- Bottom footnote ---
        Text(
            text = "SECURE INSTITUTIONAL GATEWAY",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp,
                fontSize = 10.sp,
            ),
            color = Color.White.copy(alpha = 0.4f),
            modifier = Modifier
                .alpha(contentAlpha)
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
        )
    }
}
