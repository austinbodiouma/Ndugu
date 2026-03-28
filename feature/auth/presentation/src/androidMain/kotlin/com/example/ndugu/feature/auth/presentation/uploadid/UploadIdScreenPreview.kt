package com.example.ndugu.feature.auth.presentation.uploadid

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Upload ID — No image selected", showBackground = true)
@Composable
private fun UploadIdScreenEmptyPreview() {
    MaterialTheme {
        UploadIdScreen(
            state = UploadIdState(),
            onAction = {},
        )
    }
}

@Preview(name = "Upload ID — Image selected", showBackground = true)
@Composable
private fun UploadIdScreenSelectedPreview() {
    MaterialTheme {
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
    MaterialTheme {
        UploadIdScreen(
            state = UploadIdState(
                selectedImageBytes = ByteArray(1),
                isLoading = true,
            ),
            onAction = {},
        )
    }
}
