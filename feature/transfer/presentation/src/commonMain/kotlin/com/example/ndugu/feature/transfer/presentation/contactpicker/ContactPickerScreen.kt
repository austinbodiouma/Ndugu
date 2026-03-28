package com.example.ndugu.feature.transfer.presentation.contactpicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ContactPickerRoot(
    onNavigateBack: () -> Unit,
    onNavigateToAmountEntry: (phone: String, recipientName: String) -> Unit,
    viewModel: ContactPickerViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ContactPickerEvent.NavigateBack -> onNavigateBack()
            is ContactPickerEvent.NavigateToAmountEntry -> {
                onNavigateToAmountEntry(event.phone, event.recipientName)
            }
            is ContactPickerEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    ContactPickerScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPickerScreen(
    state: ContactPickerState,
    onAction: (ContactPickerAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Send Money To") },
                navigationIcon = {
                    IconButton(onClick = { onAction(ContactPickerAction.OnBackClick) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            // Search Bar
            SearchBar(
                inputField = {
                    androidx.compose.material3.SearchBarDefaults.InputField(
                        query = state.searchQuery,
                        onQueryChange = { onAction(ContactPickerAction.OnSearchQueryChange(it)) },
                        onSearch = {},
                        expanded = false,
                        onExpandedChange = {},
                        placeholder = { Text("Search name or phone number") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    )
                },
                expanded = false,
                onExpandedChange = {},
            ) {}

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = { onAction(ContactPickerAction.OnManualEntryClick) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Enter phone number manually")
            }

            Spacer(Modifier.height(8.dp))
            Text("Contacts on CampusWallet", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(8.dp))

            LazyColumn {
                items(state.contacts, key = { it.phone }) { contact ->
                    ContactItem(
                        contact = contact,
                        onClick = { onAction(ContactPickerAction.OnContactSelected(contact)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactItem(
    contact: ContactUi,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // TODO: avatar — use Coil async image
        Column(modifier = Modifier.size(40.dp)) { /* avatar placeholder */ }

        Column(modifier = Modifier.weight(1f)) {
            Text(contact.displayName, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = contact.phone,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        if (contact.isOnCampusWallet) {
            Text(
                text = "CW",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
