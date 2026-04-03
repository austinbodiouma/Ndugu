package com.example.ndugu.feature.messaging.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatHistoryRoot(
    onNavigateBack: () -> Unit,
    onNavigateToChat: (String) -> Unit,
    onNavigateToCompose: () -> Unit,
    viewModel: ChatHistoryViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ChatHistoryEvent.NavigateBack -> onNavigateBack()
            is ChatHistoryEvent.NavigateToChat -> onNavigateToChat(event.chatId)
            is ChatHistoryEvent.NavigateToCompose -> onNavigateToCompose()
        }
    }

    ChatHistoryScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(
    state: ChatHistoryState,
    onAction: (ChatHistoryAction) -> Unit,
) {
    CampusWalletTheme {
        Scaffold(
            topBar = {
                ChatHistoryTopBar(
                    onSearchClick = { /* TODO */ },
                    onMoreClick = { /* TODO */ }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onAction(ChatHistoryAction.OnComposeNewChat) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape,
                    modifier = Modifier.padding(bottom = 80.dp) // Space for bottom nav
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "New Message",
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            bottomBar = {
                ChatHistoryBottomNavBar()
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                CategoryFilterRow(
                    selectedCategory = state.selectedCategory,
                    onCategorySelect = { onAction(ChatHistoryAction.OnCategorySelect(it)) }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.chats) { chat ->
                        ChatItemRow(
                            chat = chat,
                            onChatClick = { onAction(ChatHistoryAction.OnChatClick(chat.id)) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(100.dp)) } // Extra space for FAB/Nav
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryTopBar(
    onSearchClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Inbox",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
        navigationIcon = {
            Box(Modifier.padding(start = 16.dp)) {
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuA9zaHM8K8Hr5JAJ6SVeFd2GnOr__eGeQn3QjQtJdwMTVisMFwm-OHtqMHTrTWvPyVpfYiLSslims7H2pYS9vBF-RhKOZ0ZxrW-_vXTVrIB1aGH62sMWKArYy7XVbQT5dMNXlStfdMOQKV62wykE5_ahqSveIn6lOREJ05oDzH2Id-WhKurgLw5BQOk-x4y7WPdQ92NPP9_YbdZEMGAEyxyNRWt_1UXA-oXizLfVIBokTf3cA9BKEgYN_JbFqZ91s7KkZDV0Ek0ge4",
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = onMoreClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun CategoryFilterRow(
    selectedCategory: ChatCategory,
    onCategorySelect: (ChatCategory) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ChatCategory.entries) { category ->
            val isSelected = category == selectedCategory
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelect(category) },
                label = {
                    Text(
                        text = category.name.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " "),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                border = null,
                shape = CircleShape
            )
        }
    }
}

@Composable
fun ChatItemRow(
    chat: ChatItem,
    onChatClick: () -> Unit
) {
    val isUnread = chat.unreadCount > 0
    
    Surface(
        onClick = onChatClick,
        color = if (isUnread) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with Online indicator
            Box(contentAlignment = Alignment.BottomEnd) {
                if (chat.senderImageUrl != null) {
                    AsyncImage(
                        model = chat.senderImageUrl,
                        contentDescription = chat.senderName,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = chat.senderName.take(2).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                if (chat.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.senderName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = if (isUnread) FontWeight.Bold else FontWeight.SemiBold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = chat.timestamp,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isUnread) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                            fontWeight = if (isUnread) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
                
                Text(
                    text = chat.lastMessage,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isUnread) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (isUnread) FontWeight.SemiBold else FontWeight.Normal
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ChatHistoryBottomNavBar() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Storefront, contentDescription = "Market") },
            label = { Text("Market") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { 
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.QrCodeScanner, 
                        contentDescription = "Scan",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            label = { },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Chat, contentDescription = "Inbox") },
            label = { Text("Inbox") },
            selected = true,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { }
        )
    }
}
