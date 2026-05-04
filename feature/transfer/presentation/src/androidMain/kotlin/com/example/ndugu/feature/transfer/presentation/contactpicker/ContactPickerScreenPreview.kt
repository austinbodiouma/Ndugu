package com.example.ndugu.feature.transfer.presentation.contactpicker

import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.feature.transfer.presentation.contactpicker.ContactPickerRoot


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
    CampusWalletTheme {
        ContactPickerScreen(
            state = ContactPickerState(contacts = sampleContacts),
            onAction = {},
        )
    }
}

@Preview(name = "Contact Picker — Search active", showBackground = true)
@Composable
private fun ContactPickerScreenSearchPreview() {
    CampusWalletTheme {
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
    CampusWalletTheme {
        ContactPickerScreen(
            state = ContactPickerState(contacts = emptyList()),
            onAction = {},
        )
    }
}
