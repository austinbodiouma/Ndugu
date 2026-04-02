package com.example.ndugu.feature.auth.presentation.uploadid

import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Upload ID — No image selected", showBackground = true)
@Composable
private fun UploadIdScreenEmptyPreview() {
    CampusWalletTheme {
        UploadIdScreen(
            state = UploadIdState(),
            onAction = {},
        )
    }
}

@Preview(name = "Upload ID — Image selected", showBackground = true)
@Composable
private fun UploadIdScreenSelectedPreview() {
    CampusWalletTheme {
        UploadIdScreen(
            state = UploadIdState(
                selectedImageBytes = ByteArray(1), // non-null → shows "Image selected ✓"
            ),
            onAction = {},
        )
    }
}

@Preview(name = "Upload ID — Uploading", showBackground = true)
@Composable
private fun UploadIdScreenUploadingPreview() {
    CampusWalletTheme {
        UploadIdScreen(
            state = UploadIdState(
                selectedImageBytes = ByteArray(1),
                isLoading = true,
            ),
            onAction = {},
        )
    }
}
