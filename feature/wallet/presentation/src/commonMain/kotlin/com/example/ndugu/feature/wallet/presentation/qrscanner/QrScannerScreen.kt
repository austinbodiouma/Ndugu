package com.example.ndugu.feature.wallet.presentation.qrscanner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QrScannerRoot(
    onNavigateBack: () -> Unit,
    onNavigateToPayment: (String) -> Unit,
    viewModel: QrScannerViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is QrScannerEvent.NavigateBack -> onNavigateBack()
            is QrScannerEvent.NavigateToPayment -> onNavigateToPayment(event.receiverId)
            is QrScannerEvent.ShowError -> { /* TODO */ }
        }
    }

    QrScannerScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(
    state: QrScannerState,
    onAction: (QrScannerAction) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Mock Camera Preview
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            ScannerOverlay()
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Scan QR Code", color = Color.White, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { onAction(QrScannerAction.OnBackClick) }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            bottomBar = {
                ScannerBottomBar(onAction = onAction)
            },
            containerColor = Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(
                        text = "Align the QR code within the frame to scan",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 300.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ScannerOverlay() {
    Box(
        modifier = Modifier
            .size(260.dp)
            .border(2.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
    ) {
        // Corner indicators
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            // Top Left
            Box(Modifier.size(40.dp).align(Alignment.TopStart).border(4.dp, Color.White, RoundedCornerShape(topStart = 24.dp)))
            // Top Right
            Box(Modifier.size(40.dp).align(Alignment.TopEnd).border(4.dp, Color.White, RoundedCornerShape(topEnd = 24.dp)))
            // Bottom Left
            Box(Modifier.size(40.dp).align(Alignment.BottomStart).border(4.dp, Color.White, RoundedCornerShape(bottomStart = 24.dp)))
            // Bottom Right
            Box(Modifier.size(40.dp).align(Alignment.BottomEnd).border(4.dp, Color.White, RoundedCornerShape(bottomEnd = 24.dp)))
        }
    }
}

@Composable
private fun ScannerBottomBar(onAction: (QrScannerAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 40.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScannerActionButton(
            icon = Icons.Default.PhotoLibrary,
            label = "Gallery",
            onClick = { /* TODO */ }
        )
        ScannerActionButton(
            icon = Icons.Default.FlashlightOn,
            label = "Flash",
            onClick = { onAction(QrScannerAction.OnToggleFlashlight) }
        )
    }
}

@Composable
private fun ScannerActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White.copy(alpha = 0.15f))
        ) {
            Icon(icon, contentDescription = label, tint = Color.White)
        }
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
