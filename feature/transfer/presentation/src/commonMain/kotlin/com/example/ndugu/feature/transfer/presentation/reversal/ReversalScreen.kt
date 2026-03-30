package com.example.ndugu.feature.transfer.presentation.reversal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.ndugu.core.designsystem.components.CWTextField
import com.example.ndugu.core.designsystem.theme.*
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

// Local Design Tokens (matching RegisterScreen)
private val CWPrimary = Color(0xFF005151)
private val CWSecondaryContainer = Color(0xFFE6E9E8)
private val CWOnSurfaceVariant = Color(0xFF3F4948)

@Composable
fun ReversalRoot(
    onNavigateBack: () -> Unit,
    viewModel: ReversalViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ReversalEvent.NavigateBack -> onNavigateBack()
            is ReversalEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    ReversalScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReversalScreen(
    state: ReversalState,
    onAction: (ReversalAction) -> Unit,
) {
    Scaffold(
        topBar = {
            ReversalTopBar(onBackClick = { onAction(ReversalAction.OnBackClick) })
        },
        containerColor = Color(0xFFF7FAF9) // Premium Surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TransactionSummaryCard(
                amountKes = state.amountKes,
                recipientName = state.recipientName,
                recipientAvatarUrl = state.recipientAvatarUrl,
                dateFormatted = state.dateFormatted
            )

            Spacer(Modifier.height(32.dp))

            CountdownTimer(secondsRemaining = state.timeRemainingSeconds)

            Spacer(Modifier.height(32.dp))

            TermsCard()

            Spacer(Modifier.height(32.dp))

            CWTextField(
                label = "Reason for Reversal",
                value = state.reason,
                onValueChange = { onAction(ReversalAction.OnReasonChange(it)) },
                placeholder = "e.g. Sent to the wrong person",
                singleLine = false,
                modifier = Modifier.heightIn(min = 120.dp)
            )

            Spacer(Modifier.height(40.dp))

            PrimaryActionButton(
                text = "Request Reversal",
                isLoading = state.isLoading,
                onClick = { onAction(ReversalAction.OnConfirmReversalClick) }
            )

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = { onAction(ReversalAction.OnCancelClick) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Cancel Request",
                    color = CWPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReversalTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                "Request Reversal",
                color = CWPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = CWPrimary)
            }
        },
        actions = {
            IconButton(onClick = { /* Profile click */ }) {
                Icon(
                    Icons.Rounded.AccountCircle,
                    contentDescription = "Profile",
                    tint = CWOnSurfaceVariant,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
private fun TransactionSummaryCard(
    amountKes: String,
    recipientName: String,
    recipientAvatarUrl: String?,
    dateFormatted: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(CWPrimary, Color(0xFF0E6B6B))
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "You are reversing",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "KES $amountKes",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.height(24.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                if (recipientAvatarUrl != null) {
                    AsyncImage(
                        model = recipientAvatarUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Rounded.AccountCircle, null, tint = Color.White)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        recipientName,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        dateFormatted,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CountdownTimer(secondsRemaining: Int) {
    val minutes = secondsRemaining / 60
    val seconds = secondsRemaining % 60
    val timeStr = "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = timeStr,
            fontSize = 48.sp,
            fontWeight = FontWeight.Light,
            color = CWPrimary,
            letterSpacing = 4.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Remaining time to request reversal",
            fontSize = 12.sp,
            color = CWOnSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TermsCard() {
    Surface(
        color = CWSecondaryContainer,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = null,
                tint = CWPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                "The recipient must approve this request before funds can be reversed. " +
                "Marketplace transactions have additional dispute protections.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = CWOnSurfaceVariant
            )
        }
    }
}

@Composable
private fun PrimaryActionButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(CWPrimary, Color(0xFF0E6B6B))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
