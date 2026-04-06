package com.example.ndugu.feature.marketplace.presentation.tracking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OrderTrackingRoot(
    onNavigateBack: () -> Unit,
    onNavigateToChat: (String) -> Unit,
    viewModel: OrderTrackingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is OrderTrackingEvent.NavigateBack -> onNavigateBack()
            is OrderTrackingEvent.NavigateToChat -> onNavigateToChat(event.sellerId)
        }
    }

    OrderTrackingScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(
    state: OrderTrackingState,
    onAction: (OrderTrackingAction) -> Unit,
) {
    CampusWalletTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Order #${state.orderId}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onAction(OrderTrackingAction.OnBackClick) }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
            ) {
                // Status Banner
                item {
                    StatusBanner(
                        status = state.status.name.replace("_", " ").lowercase().capitalize(),
                        description = state.statusDescription
                    )
                }

                // Item Summary Card
                item {
                    state.item?.let { item ->
                        ItemSummaryCard(item = item)
                    }
                }

                // Escrow Timeline
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ESCROW TIMELINE",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        EscrowTimeline(steps = state.timeline)
                    }
                }

                // Message Seller Link
                item {
                    TextButton(
                        onClick = { onAction(OrderTrackingAction.OnMessageSeller) },
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.ChatBubble, contentDescription = null, modifier = Modifier.size(20.dp))
                            Text("Message Seller", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Action Buttons
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { onAction(OrderTrackingAction.OnConfirmReceipt) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = CircleShape,
                            enabled = state.status == OrderStatus.SELLER_CONFIRMED,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.6f)
                            )
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Confirm Receipt", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { onAction(OrderTrackingAction.OnRaiseDispute) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = CircleShape,
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.2f)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(Icons.Default.Report, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Raise Dispute", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Info Footer
                item {
                    Text(
                        text = "\"Confirm Receipt\" will be enabled once the seller marks the item as delivered. Funds are protected for 7 days after delivery.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBanner(status: String, description: String) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Inventory2,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = status,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
private fun ItemSummaryCard(item: TrackedItem) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sold by ${item.sellerName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$${item.price}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = item.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
private fun EscrowTimeline(steps: List<TimelineStep>) {
    Box(modifier = Modifier.fillMaxWidth()) {
        // Shared Progress Line
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .height(4.dp)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh, CircleShape)
        ) {
            // In a real app, this would be computed based on active index
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            steps.forEach { step ->
                TimelineStepItem(
                    step = step,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TimelineStepItem(step: TimelineStep, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    when (step.status) {
                        StepStatus.COMPLETED -> MaterialTheme.colorScheme.primary
                        StepStatus.ACTIVE -> MaterialTheme.colorScheme.tertiaryContainer
                        StepStatus.UPCOMING -> MaterialTheme.colorScheme.surfaceContainerHigh
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (step.icon) {
                    "check" -> Icons.Default.Check
                    "inventory_2" -> Icons.Default.Inventory2
                    "local_shipping" -> Icons.Default.LocalShipping
                    "verified" -> Icons.Default.Verified
                    else -> Icons.Default.Check
                },
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = when (step.status) {
                    StepStatus.COMPLETED -> Color.White
                    StepStatus.ACTIVE -> MaterialTheme.colorScheme.tertiary
                    StepStatus.UPCOMING -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                }
            )
        }
        Text(
            text = step.title.replace(" ", "\n"),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (step.status == StepStatus.ACTIVE) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
            color = if (step.status == StepStatus.UPCOMING) {
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

// Extension to bridge capitalize() which is deprecated or missing in some environments
private fun String.capitalize(): String = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
