package com.example.ndugu.feature.marketplace.presentation.seller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import com.example.ndugu.core.presentation.ObserveAsEvents
import com.example.ndugu.feature.marketplace.presentation.home.ListingCard
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardRoot(
    onNavigateBack: () -> Unit,
    onNavigateToCreateListing: () -> Unit,
    onNavigateToListingDetail: (String) -> Unit,
    viewModel: SellerDashboardViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SellerDashboardEvent.NavigateBack -> onNavigateBack()
            is SellerDashboardEvent.NavigateToCreateListing -> onNavigateToCreateListing()
            is SellerDashboardEvent.NavigateToListingDetail -> onNavigateToListingDetail(event.id)
        }
    }

    SellerDashboardScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardScreen(
    state: SellerDashboardState,
    onAction: (SellerDashboardAction) -> Unit,
) {
    CampusWalletTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Seller Dashboard",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onAction(SellerDashboardAction.OnBackClick) }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.MoreHoriz, contentDescription = "More")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onAction(SellerDashboardAction.OnCreateListingClick) },
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.AddAPhoto, contentDescription = "Create Listing")
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Profile Info
                item(span = { GridItemSpan(2) }) {
                    ProfileHeader(
                        name = state.sellerName,
                        avatarUrl = state.sellerAvatarUrl,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Stats Cards
                item(span = { GridItemSpan(2) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatsCard(
                            label = "Revenue",
                            value = "KES ${state.totalRevenue.toInt()}",
                            icon = Icons.Default.Payment,
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.weight(1f)
                        )
                        StatsCard(
                            label = "Active",
                            value = "${state.activeListingsCount}",
                            icon = Icons.Default.Storefront,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.weight(1f)
                        )
                        StatsCard(
                            label = "Orders",
                            value = "${state.pendingOrdersCount}",
                            icon = Icons.Default.ShoppingBag,
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Section Title: My Listings
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "My Active Listings",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }

                // Listings Grid
                items(state.myListings) { listing ->
                    ListingCard(
                        listing = listing,
                        onListingClick = { onAction(SellerDashboardAction.OnListingClick(listing.id)) },
                        onFavoriteClick = {} // My own listings don't need favorite on dashboard?
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    avatarUrl: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "Profile Photo",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Welcome back,",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}

@Composable
fun StatsCard(
    label: String,
    value: String,
    icon: ImageVector,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                )
            }
        }
    }
}

