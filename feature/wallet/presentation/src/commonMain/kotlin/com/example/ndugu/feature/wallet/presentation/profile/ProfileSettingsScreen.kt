package com.example.ndugu.feature.wallet.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.ndugu.core.designsystem.theme.*
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileSettingsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: ProfileSettingsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ProfileSettingsEvent.NavigateBack -> onNavigateBack()
            is ProfileSettingsEvent.NavigateToLogin -> onNavigateToLogin()
        }
    }

    ProfileSettingsScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
    state: ProfileSettingsState,
    onAction: (ProfileSettingsAction) -> Unit,
) {
    Scaffold(
        topBar = {
            ProfileTopAppBar(
                onNotificationClick = { onAction(ProfileSettingsAction.OnNotificationsClick) }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                ProfileHeroCard(
                    state = state,
                    modifier = Modifier.padding(24.dp)
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Account Section
                    ProfileSection(title = "Account") {
                        ProfileItem(
                            icon = Icons.Outlined.Person,
                            title = "Personal Info",
                            subtitle = "Update your details",

                            iconColor = MaterialTheme.colorScheme.primary,
                            onClick = { onAction(ProfileSettingsAction.OnPersonalInfoClick) }
                        )
                    }

                    // Wallet Section
                    ProfileSection(title = "Wallet") {
                        ProfileItem(
                            icon = Icons.Outlined.Analytics,
                            title = "Budgets",
                            subtitle = "Manage campus spending",

                            iconColor = MaterialTheme.colorScheme.secondary,
                            onClick = { onAction(ProfileSettingsAction.OnBudgetsClick) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceContainer)
                        ProfileItem(
                            icon = Icons.Outlined.History,
                            title = "Transaction History",
                            subtitle = "All past payments",

                            iconColor = MaterialTheme.colorScheme.secondary,
                            onClick = { onAction(ProfileSettingsAction.OnTransactionHistoryClick) }
                        )
                    }

                    // Marketplace Section
                    ProfileSection(title = "Marketplace") {
                        ProfileItem(
                            icon = Icons.Outlined.ShoppingBag,
                            title = "Shop",
                            subtitle = "Campus bookstore & gear",

                            iconColor = MaterialTheme.colorScheme.tertiary,
                            onClick = { onAction(ProfileSettingsAction.OnShopClick) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceContainer)
                        ProfileItem(
                            icon = Icons.Outlined.ReceiptLong,
                            title = "Your Orders",
                            subtitle = "Track your deliveries",

                            iconColor = MaterialTheme.colorScheme.tertiary,
                            onClick = { onAction(ProfileSettingsAction.OnYourOrdersClick) }
                        )
                    }

                    // Preferences Section
                    ProfileSection(title = "Preferences") {
                        ProfileItem(
                            icon = Icons.Outlined.NotificationsActive,
                            title = "Notifications",
                            subtitle = "Alerts and reminders",

                            iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            onClick = { onAction(ProfileSettingsAction.OnNotificationsClick) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceContainer)
                        ProfileItem(
                            icon = Icons.Outlined.Security,
                            title = "Security",
                            subtitle = "FaceID, PIN, and Privacy",

                            iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            onClick = { onAction(ProfileSettingsAction.OnSecurityClick) }
                        )
                    }

                    // Support & Logout Section
                    ProfileSection {
                        ProfileItem(
                            icon = Icons.Outlined.Help,
                            title = "Support",
                            subtitle = "24/7 Academic concierge",

                            iconColor = MaterialTheme.colorScheme.primary,
                            onClick = { onAction(ProfileSettingsAction.OnSupportClick) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceContainer)
                        ProfileItem(
                            icon = Icons.Outlined.Logout,
                            title = "Log Out",
                            titleColor = MaterialTheme.colorScheme.primary, // Matching HTML Logout Text Color
                            subtitle = "v4.2.1",

                            iconColor = MaterialTheme.colorScheme.error,
                            onClick = { onAction(ProfileSettingsAction.OnLogoutClick) },
                            showChevron = false,
                            subtitleAlignment = Alignment.End
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopAppBar(
    onNotificationClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Using a placeholder for the logo as seen in HTML
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    // In a real app, this would be an Image or Icon
                }
                Text(
                    text = "CampusWallet",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = (-0.5).sp
                    )
                )
            }
        },
        actions = {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
private fun ProfileHeroCard(
    state: ProfileSettingsState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer)
                )
            )
    ) {
        // Decorative circles for glassmorphism feel
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = 180.dp, y = (-40).dp)
                .blur(40.dp)
                .background(Color.White.copy(alpha = 0.1f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = (-40).dp, y = 140.dp)
                .blur(30.dp)
                .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(
                    model = state.avatarUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(4.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                if (state.isVerified) {
                    Surface(
                        modifier = Modifier.offset(x = 4.dp, y = 4.dp),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Verified",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = state.displayName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            Text(
                text = state.email,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${state.university} • Class of ${state.classOf}".uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}

@Composable
private fun ProfileSection(
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        if (title != null) {
            Text(
                text = title.uppercase(),
                modifier = Modifier.padding(start = 8.dp, bottom = 12.dp),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    letterSpacing = 2.sp
                )
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        ) {
            Column(content = content)
        }
    }
}

@Composable
private fun ProfileItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    onClick: () -> Unit,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    showChevron: Boolean = true,
    subtitleAlignment: Alignment.Horizontal = Alignment.Start
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerLow),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
            )
            if (subtitleAlignment == Alignment.Start) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        if (subtitleAlignment == Alignment.End) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        if (showChevron) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.offset(x = 4.dp)
            )
        }
    }
}
