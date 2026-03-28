package com.example.ndugu.feature.transfer.presentation.contactpicker

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

private val sampleContacts = listOf(
    ContactUi("+254712111111", "Alice Kamau", isOnCampusWallet = true),
    ContactUi("+254722222222", "Brian Otieno", isOnCampusWallet = true),
    ContactUi("+254733333333", "Carol Mwangi", isOnCampusWallet = false),
    ContactUi("+254744444444", "David Kipchoge", isOnCampusWallet = true),
    ContactUi("+254755555555", "Eve Achieng", isOnCampusWallet = false),
)

@Preview(name = "Contact Picker — Loaded", showBackground = true)
@Composable
private fun ContactPickerScreenPreview() {
    MaterialTheme {
        ContactPickerScreen(
            state = ContactPickerState(contacts = sampleContacts),
            onAction = {},
        )
    }
}

@Preview(name = "Contact Picker — Search active", showBackground = true)
@Composable
private fun ContactPickerScreenSearchPreview() {
    MaterialTheme {
        ContactPickerScreen(
            state = ContactPickerState(
                searchQuery = "Brian",
                contacts = sampleContacts.filter { it.displayName.contains("Brian") },
            ),
            onAction = {},
        )
    }
}

@Preview(name = "Contact Picker — Empty list", showBackground = true)
@Composable
private fun ContactPickerScreenEmptyPreview() {
    MaterialTheme {
        ContactPickerScreen(
            state = ContactPickerState(contacts = emptyList()),
            onAction = {},
        )
    }
}
