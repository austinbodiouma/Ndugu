package com.example.ndugu.feature.auth.presentation.uploadid

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UploadIdRoot(
    onNavigateToHome: () -> Unit,
    viewModel: UploadIdViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is UploadIdEvent.NavigateToHome -> onNavigateToHome()
            is UploadIdEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    UploadIdScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun UploadIdScreen(
    state: UploadIdState,
    onAction: (UploadIdAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Verify Your Student ID",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Upload a clear photo of your student ID card to unlock full marketplace access.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(32.dp))

        // ID photo preview placeholder
        Box(
            modifier = Modifier
                .size(240.dp, 150.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (state.selectedImageBytes != null) {
                // TODO: render image from bytes using platform image util
                Text("Image selected ✓")
            } else {
                Text(
                    "Tap to select photo",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = { onAction(UploadIdAction.OnPickImageClick) }) {
            Text(if (state.selectedImageBytes != null) "Change Photo" else "Select Photo")
        }

        Spacer(Modifier.height(24.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { onAction(UploadIdAction.OnUploadClick) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.selectedImageBytes != null,
            ) {
                Text("Submit for Verification")
            }
            Spacer(Modifier.height(12.dp))
            TextButton(onClick = { onAction(UploadIdAction.OnSkipClick) }) {
                Text("Skip for now — I'll do this later")
            }
        }
    }
}
