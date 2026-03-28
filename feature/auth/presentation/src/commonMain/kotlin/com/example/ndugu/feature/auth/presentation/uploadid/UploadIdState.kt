package com.example.ndugu.feature.auth.presentation.uploadid

import com.example.ndugu.core.presentation.UiText

data class UploadIdState(
    val selectedImageBytes: ByteArray? = null,
    val isLoading: Boolean = false,
    val isUploaded: Boolean = false,
    val error: UiText? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UploadIdState) return false
        return isLoading == other.isLoading &&
            isUploaded == other.isUploaded &&
            error == other.error &&
            selectedImageBytes.contentEquals(other.selectedImageBytes)
    }

    override fun hashCode(): Int {
        var result = selectedImageBytes?.contentHashCode() ?: 0
        result = 31 * result + isLoading.hashCode()
        result = 31 * result + isUploaded.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }
}

private fun ByteArray?.contentEquals(other: ByteArray?): Boolean {
    if (this == null && other == null) return true
    if (this == null || other == null) return false
    return this.contentEquals(other)
}

sealed interface UploadIdAction {
    data class OnImageSelected(val bytes: ByteArray) : UploadIdAction
    data object OnPickImageClick : UploadIdAction
    data object OnUploadClick : UploadIdAction
    data object OnSkipClick : UploadIdAction
}

sealed interface UploadIdEvent {
    data object NavigateToHome : UploadIdEvent
    data class ShowSnackbar(val message: UiText) : UploadIdEvent
}
