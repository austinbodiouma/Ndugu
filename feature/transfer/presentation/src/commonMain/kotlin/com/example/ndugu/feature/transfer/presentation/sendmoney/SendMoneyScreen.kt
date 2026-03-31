package com.example.ndugu.feature.transfer.presentation.sendmoney

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.AccountBalanceWallet
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
import com.example.ndugu.core.designsystem.theme.CWPremiumSurface
import com.example.ndugu.core.designsystem.theme.CWPremiumTeal
import com.example.ndugu.core.designsystem.theme.CWPremiumTealContainer
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SendMoneyRoot(
    onNavigateBack: () -> Unit,
    onNavigateToSuccess: (String) -> Unit,
    viewModel: SendMoneyViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SendMoneyEvent.NavigateBack -> onNavigateBack()
            is SendMoneyEvent.NavigateToSuccess -> onNavigateToSuccess(event.transactionId)
            is SendMoneyEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    SendMoneyScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(
    state: SendMoneyState,
    onAction: (SendMoneyAction) -> Unit,
) {
    Scaffold(
        topBar = {
            SendMoneyTopBar(
                isConfirmStep = state.isConfirmStep,
                onBackClick = { onAction(SendMoneyAction.OnBackClick) }
            )
        },
        containerColor = CWPremiumSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isConfirmStep) {
                ConfirmTransferContent(state = state, onAction = onAction)
            } else {
                AmountEntryContent(state = state, onAction = onAction)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SendMoneyTopBar(isConfirmStep: Boolean, onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            if (isConfirmStep) {
                Text(
                    text = "Send Money",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = CWPremiumTeal
                    )
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Send Money",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = CWPremiumTeal
                        )
                    )
                    Text(
                        text = "STEP 2 OF 3",
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.LightGray
                        )
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = CWPremiumTeal
                )
            }
        },
        actions = {
            if (isConfirmStep) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("STEP", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp, color = CWPremiumTeal))
                        Text("3 of 3", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold, color = CWPremiumTeal))
                    }
                }
                Spacer(Modifier.width(16.dp))
            } else {
                Spacer(Modifier.width(48.dp)) // Symmetry spacer
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = CWPremiumSurface)
    )
}

@Composable
private fun AmountEntryContent(
    state: SendMoneyState,
    onAction: (SendMoneyAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        // Recipient Chip
        RecipientChip(name = state.recipientName, avatarUrl = state.recipientAvatarUrl)

        Spacer(Modifier.weight(1f))

        // Amount Display
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "KES",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = CWPremiumTeal.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = state.amountKes.ifBlank { "0.00" },
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = CWPremiumTeal,
                        letterSpacing = (-2).sp
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            // Balance Badge
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = CircleShape
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountBalanceWallet,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Balance: KES ${state.availableBalanceKes.ifBlank { "12,450.00" }}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        // Note Input
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Notes,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.width(12.dp))
                TextField(
                    value = state.memo,
                    onValueChange = { onAction(SendMoneyAction.OnMemoChange(it)) },
                    placeholder = {
                        Text(
                            "Add a note (optional)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    singleLine = true
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Numeric Keypad and CTA
        NumericKeypadArea(onAction = onAction)
        
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun RecipientChip(name: String, avatarUrl: String?) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = CircleShape,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer)) {
                if (avatarUrl != null) {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(name.take(1).uppercase(), style = MaterialTheme.typography.titleMedium, color = CWPremiumTeal)
                    }
                }
            }
            Column {
                Text("Sending to", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text(name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = CWPremiumTeal))
            }
        }
    }
}

@Composable
private fun NumericKeypadArea(onAction: (SendMoneyAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(Color.White.copy(alpha = 0.8f))
            .padding(horizontal = 32.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        val keys = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf(".", "0", "backspace")
        )

        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { key ->
                    KeypadButton(
                        key = key,
                        onClick = {
                            if (key == "backspace") {
                                onAction(SendMoneyAction.OnKeypadBackspace)
                            } else {
                                onAction(SendMoneyAction.OnKeypadDigit(key))
                            }
                        }
                    )
                }
            }
        }

        Button(
            onClick = { onAction(SendMoneyAction.OnReviewClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(listOf(CWPremiumTeal, CWPremiumTealContainer)),
                        shape = RoundedCornerShape(32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Continue", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}

@Composable
private fun KeypadButton(key: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (key == "backspace") {
            Icon(Icons.Default.Backspace, contentDescription = "Delete", tint = CWPremiumTeal)
        } else {
            Text(
                text = key,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = CWPremiumTeal
                )
            )
        }
    }
}

@Composable
private fun ConfirmTransferContent(
    state: SendMoneyState,
    onAction: (SendMoneyAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))

        // Title & Subtitle
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Review Transfer",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Please verify the recipient details before confirming the transaction.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(24.dp))

        // Amount Hero Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Brush.linearGradient(listOf(CWPremiumTeal, CWPremiumTealContainer)))
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "TOTAL TRANSFER",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.sp,
                        color = Color(0xFF86d4d3) // primary-fixed-dim approx
                    )
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "KES",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF86d4d3)
                        )
                    )
                    Text(
                        text = state.amountKes.ifBlank { "0.00" },
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = (-1).sp
                        )
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Transfer Details Grid
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Recipient Info
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer)) {
                            if (state.recipientAvatarUrl != null) {
                                AsyncImage(
                                    model = state.recipientAvatarUrl,
                                    contentDescription = state.recipientName,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Column {
                            Text("To", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium), color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(state.recipientName, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                            Text(if (state.recipientPhone.isNotBlank()) state.recipientPhone else "No phone", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Icon(Icons.Default.Verified, contentDescription = "Verified", tint = CWPremiumTealContainer)
                }
            }

            // Source Wallet
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                        Column {
                            Text("From", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium), color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Your CampusWallet", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Icon(Icons.Outlined.AccountBalanceWallet, contentDescription = "Wallet", tint = MaterialTheme.colorScheme.secondary)
                    }
                    Spacer(Modifier.height(16.dp))
                    
                    // Progress Bar showing remaining balance
                    Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceContainer)) {
                        Box(modifier = Modifier.fillMaxWidth(0.65f).fillMaxHeight().background(CWPremiumTeal))
                    }
                    
                    Spacer(Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Remaining Balance", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        // In a real app, calculate this correctly
                        Text("KES ${state.availableBalanceKes.ifBlank { "12,450.00" }}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }

            // Note Card
            if (state.memo.isNotBlank()) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Notes, contentDescription = "Note", tint = MaterialTheme.colorScheme.tertiary)
                        Column {
                            Text("Note", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium), color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("\"${state.memo}\"", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(top = 2.dp), fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Warning Banner
        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = "Info", tint = MaterialTheme.colorScheme.tertiary)
                Text(
                    text = "Transfer Lock: Transactions can be reversed within 30 minutes if a dispute is raised.",
                    style = MaterialTheme.typography.labelSmall.copy(lineHeight = 16.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.weight(1f))

        // Biometric Prompt
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(64.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Fingerprint, contentDescription = "Biometrics", tint = CWPremiumTeal, modifier = Modifier.size(32.dp))
            }
            Spacer(Modifier.height(8.dp))
            Text("Biometric Authentication Required", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
            Text("Touch the sensor to verify payment.", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Spacer(Modifier.height(24.dp))

        // Actions
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { onAction(SendMoneyAction.OnConfirmSendClick) },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.horizontalGradient(listOf(CWPremiumTeal, CWPremiumTealContainer))),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Confirm & Send", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", modifier = Modifier.size(18.dp))
                    }
                }
            }
            
            TextButton(
                onClick = { onAction(SendMoneyAction.OnEditClick) },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(
                    "Cancel",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
        
        Spacer(Modifier.height(32.dp))
    }
}
