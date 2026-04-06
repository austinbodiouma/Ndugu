package com.example.ndugu.feature.auth.presentation.verificationpending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun VerificationPendingRoot(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: VerificationPendingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is VerificationPendingEvent.NavigateBack -> onNavigateBack()
            is VerificationPendingEvent.NavigateToHome -> onNavigateToHome()
        }
    }

    VerificationPendingScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationPendingScreen(
    state: VerificationPendingState,
    onAction: (VerificationPendingAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Verification Pending") })
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.HourglassTop,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Verification in Progress",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Your identity documents are being reviewed. This usually takes 24–48 hours. We'll notify you once your account is verified.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onAction(VerificationPendingAction.OnCheckStatusClick) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Check Status")
                }
                OutlinedButton(
                    onClick = { onAction(VerificationPendingAction.OnLogoutClick) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Log Out")
                }
            }
        }
    }
}
