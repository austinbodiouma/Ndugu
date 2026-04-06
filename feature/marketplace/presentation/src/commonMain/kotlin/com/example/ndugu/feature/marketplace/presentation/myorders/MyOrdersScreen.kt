package com.example.ndugu.feature.marketplace.presentation.myorders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun MyOrdersRoot(
    onNavigateBack: () -> Unit,
    onNavigateToOrderDetail: (String) -> Unit,
    onNavigateToTracking: (String) -> Unit,
    onNavigateToChat: (String) -> Unit,
    viewModel: MyOrdersViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is MyOrdersEvent.NavigateBack -> onNavigateBack()
            is MyOrdersEvent.NavigateToOrderDetail -> onNavigateToOrderDetail(event.orderId)
            is MyOrdersEvent.NavigateToTracking -> onNavigateToTracking(event.orderId)
            is MyOrdersEvent.NavigateToChat -> onNavigateToChat(event.sellerId)
        }
    }

    MyOrdersScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(
    state: MyOrdersState,
    onAction: (MyOrdersAction) -> Unit,
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = null,
                                tint = CWPrimary
                            )
                            Text(
                                "Campus Marketplace",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CWPrimary
                                )
                            )
                        }
                    },
                    actions = {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAj6RLtgQMWjmBxWI1D6Yrb-Lms27pxzgrf45Mklbc9m1AJPKpshQUoGQngMh059pf23xGrKl_YCqHI_XFuxZ3LZUs6pQcFpf1341XQ2ea5mgcH6XFFVzOT-IpneqxA19zQ6zjPdM5DS6eHuzvZLsSv2vXO1mdiYHeaqWS32s0pptfTVdb8JjlHBqeNnEJj3Ce04Mt3h1GaczYDE2mht_CWO5SYCm3PwLsp2bf7V3-4EQ2eyX4wAsYY6Fet-WSg6-V3Kc4rOCney-U",
                            contentDescription = "Profile",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CWBackground
                    )
                )
                HorizontalDivider(color = CWSurfaceContainer)
            }
        },
        containerColor = CWBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                DashboardSummary(
                    activeCount = state.activeCount,
                    inTransitCount = state.inTransitCount,
                    pendingCount = state.pendingCount
                )
            }

            item {
                TabSelector(
                    selectedTab = state.selectedTab,
                    onTabSelected = { onAction(MyOrdersAction.OnTabSelected(it)) }
                )
            }

            if (state.orders.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No orders in this category",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CWOnSurfaceVariant
                        )
                    }
                }
            } else {
                items(state.orders, key = { it.id }) { order ->
                    OrderItemCard(
                        order = order,
                        onAction = { onAction(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardSummary(
    activeCount: Int,
    inTransitCount: Int,
    pendingCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(CWPrimary, CWPrimaryContainer)
                )
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                "ACTIVE TRANSACTIONS",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 2.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            )
            Text(
                "Tracking $activeCount items",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                GlassChip(
                    icon = Icons.Default.LocalShipping,
                    text = "$inTransitCount in transit"
                )
                GlassChip(
                    icon = Icons.Default.PendingActions,
                    text = "$pendingCount pending"
                )
            }
        }
        
        // Decorative circle
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = 40.dp)
                .size(160.dp)
                .background(Color.White.copy(alpha = 0.1f), CircleShape)
        )
    }
}

@Composable
private fun GlassChip(
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.White
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        )
    }
}

@Composable
private fun TabSelector(
    selectedTab: OrderTab,
    onTabSelected: (OrderTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(CWSurfaceContainerLow)
            .padding(4.dp)
    ) {
        OrderTab.entries.forEach { tab ->
            val isSelected = selectedTab == tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(if (isSelected) CWOnPrimary else Color.Transparent)
                    .clickable { onTabSelected(tab) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) CWPrimary else CWOnSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
private fun OrderItemCard(
    order: OrderItem,
    onAction: (MyOrdersAction) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = CWOutlineVariant.copy(alpha = 0.15f),
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CWSurfaceContainerLowest)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    AsyncImage(
                        model = order.imageUrl,
                        contentDescription = order.title,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CWSurfaceContainer),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(
                            order.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            "Seller: ${order.sellerName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = CWOnSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "$${order.amount}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = CWPrimary
                            )
                        )
                    }
                }
                
                StatusChip(status = order.status)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = CWSurfaceContainer)
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val statusIcon = when (order.status) {
                        OrderStatus.UNDER_REVIEW -> Icons.Default.Info
                        else -> Icons.Default.Schedule
                    }
                    val iconTint = if (order.status == OrderStatus.UNDER_REVIEW) CWTertiary else CWOnSurfaceVariant
                    
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = iconTint
                    )
                    Text(
                        order.deliveryStatusText,
                        style = MaterialTheme.typography.bodySmall,
                        color = CWOnSurfaceVariant
                    )
                }
                
                when (order.actionType) {
                    OrderActionType.TRACK -> {
                        TextButton(
                            onClick = { onAction(MyOrdersAction.OnTrackClick(order.id)) },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "Track",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CWPrimary
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = CWPrimary
                            )
                        }
                    }
                    OrderActionType.DETAILS -> {
                        TextButton(
                            onClick = { onAction(MyOrdersAction.OnOrderClick(order.id)) },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "Details",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CWOnSurfaceVariant
                                )
                            )
                        }
                    }
                    OrderActionType.MESSAGE_SELLER -> {
                        Button(
                            onClick = { onAction(MyOrdersAction.OnMessageSeller(order.id)) },
                            colors = ButtonDefaults.buttonColors(containerColor = CWPrimary),
                            shape = CircleShape,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text(
                                "Message Seller",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusChip(status: OrderStatus) {
    val (containerColor, contentColor) = when (status) {
        OrderStatus.IN_PROGRESS -> CWPrimaryFixed to CWOnPrimaryFixedVariant
        OrderStatus.UNDER_REVIEW -> CWTertiaryFixed to CWOnTertiaryFixedVariant
        OrderStatus.PICKUP -> CWPrimaryFixed to CWOnPrimaryFixedVariant
        OrderStatus.COMPLETED -> CWOnPrimaryContainer to CWPrimary
        OrderStatus.DISPUTED -> CWErrorContainer to CWOnErrorContainer
    }
    
    Surface(
        color = containerColor,
        shape = CircleShape,
        contentColor = contentColor
    ) {
        Text(
            text = status.name.replace("_", " "),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        )
    }
}
