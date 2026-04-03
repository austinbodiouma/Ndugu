package com.example.ndugu.feature.wallet.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WalletDashboardRoot(
    onNavigateToTopUp: () -> Unit,
    onNavigateToSendMoney: () -> Unit,
    onNavigateToScanQr: () -> Unit,
    onNavigateToRequestMoney: () -> Unit,
    onNavigateToTransactionHistory: () -> Unit,
    onNavigateToTransactionDetail: (String) -> Unit,
    viewModel: WalletDashboardViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is WalletDashboardEvent.NavigateToTopUp -> onNavigateToTopUp()
            is WalletDashboardEvent.NavigateToSendMoney -> onNavigateToSendMoney()
            is WalletDashboardEvent.NavigateToScanQr -> onNavigateToScanQr()
            is WalletDashboardEvent.NavigateToRequestMoney -> onNavigateToRequestMoney()
            is WalletDashboardEvent.NavigateToTransactionHistory -> onNavigateToTransactionHistory()
            is WalletDashboardEvent.NavigateToTransactionDetail -> onNavigateToTransactionDetail(event.transactionId)
            is WalletDashboardEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    WalletDashboardScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun WalletDashboardScreen(
    state: WalletDashboardState,
    onAction: (WalletDashboardAction) -> Unit,
) {
    Scaffold(
        topBar = {
            DashboardTopBar(
                userName = state.userName,
                profileImageUrl = state.profileImageUrl,
                onNotificationClick = { /* TODO */ }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item {
                    BalanceCard(
                        balance = state.balanceKes,
                        changePercentage = state.balanceChangePercentage,
                        isBalanceVisible = state.isBalanceVisible,
                        onToggleVisibility = { onAction(WalletDashboardAction.OnToggleBalanceVisibility) }
                    )
                }

                item {
                    QuickActionGrid(
                        onAction = onAction
                    )
                }

                item {
                    TransactionListSection(
                        transactions = state.recentTransactions,
                        onSeeAllClick = { onAction(WalletDashboardAction.OnSeeAllTransactionsClick) },
                        onTransactionClick = { onAction(WalletDashboardAction.OnTransactionClick(it)) }
                    )
                }

                item {
                    BudgetSummarySection(
                        budgets = state.budgets
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardTopBar(
    userName: String,
    profileImageUrl: String?,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = profileImageUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "Good morning, $userName 👋",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        Box {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            // Badge
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp, y = 8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFB955)) // tertiary-fixed-dim

                    .border(2.dp, MaterialTheme.colorScheme.background, CircleShape)
            )
        }
    }
}

@Composable
private fun BalanceCard(
    balance: String,
    changePercentage: String,
    isBalanceVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    val meshGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.primary
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(meshGradient)
                .padding(24.dp)
        ) {
            // Decorative Circle Effect
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .offset(x = 100.dp, y = (-80).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "AVAILABLE BALANCE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                letterSpacing = 2.sp,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = if (isBalanceVisible) "KES $balance" else "KES ••••••",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                            IconButton(onClick = onToggleVisibility) {
                                Icon(
                                    imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle Balance",
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    // Glass Card Wallet Icon
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .padding(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccountBalanceWallet,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.TrendingUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primaryContainer, // primary-fixed
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = changePercentage,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionGrid(
    onAction: (WalletDashboardAction) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        QuickActionItem(
            icon = Icons.Outlined.AddCircle,
            label = "Top Up",
            onClick = { onAction(WalletDashboardAction.OnTopUpClick) }
        )
        QuickActionItem(
            icon = Icons.Outlined.Send,
            label = "Send",
            onClick = { onAction(WalletDashboardAction.OnSendMoneyClick) }
        )
        QuickActionItem(
            icon = Icons.Outlined.QrCodeScanner,
            label = "Scan",
            isPrimary = true,
            onClick = { onAction(WalletDashboardAction.OnScanQrClick) }
        )
        QuickActionItem(
            icon = Icons.Outlined.RequestQuote,
            label = "Request",
            onClick = { onAction(WalletDashboardAction.OnRequestMoneyClick) }
        )
    }
}

@Composable
private fun QuickActionItem(
    icon: ImageVector,
    label: String,
    isPrimary: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        val containerColor = if (isPrimary) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerLowest
        val iconColor = if (isPrimary) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.primary

        Box(
            modifier = Modifier
                .size(if (isPrimary) 64.dp else 56.dp)
                .clip(CircleShape)
                .background(containerColor)
                .shadowIfPrimary(isPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            ),
            fontSize = 10.sp
        )
    }
}

private fun Modifier.shadowIfPrimary(isPrimary: Boolean): Modifier = if (isPrimary) {
    this // TODO: Add elevation if possible or custom shadow
} else this

@Composable
private fun TransactionListSection(
    transactions: List<TransactionUi>,
    onSeeAllClick: () -> Unit,
    onTransactionClick: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold)
            )
            Text(
                text = "See All",
                modifier = Modifier.clickable(onClick = onSeeAllClick),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            transactions.take(5).forEach { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = { onTransactionClick(transaction.id) }
                )
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: TransactionUi,
    onClick: () -> Unit
) {
    val iconContainerColor = when (transaction.iconType) {
        TransactionIconType.PERSON -> Color(0xFFE1DFFF)
        TransactionIconType.BANK -> Color(0xFFA2F0EF) // primary-fixed
        TransactionIconType.RESTAURANT -> Color(0xFFFFDDB4) // tertiary-fixed
        TransactionIconType.SHOPPING_BAG -> Color(0xFFE1DFFF) // secondary-fixed
        TransactionIconType.PAYMENTS -> Color(0xFFA2F0EF) // primary-fixed
        TransactionIconType.ADD_CARD -> Color(0xFFA2F0EF)
        TransactionIconType.MENU_BOOK -> Color(0xFFFFDDB4)
        TransactionIconType.LOCAL_PRINTSHOP -> Color(0xFFE1DFFF)
    }
    
    val iconImage = when (transaction.iconType) {
        TransactionIconType.PERSON -> Icons.Outlined.Person
        TransactionIconType.BANK -> Icons.Outlined.AccountBalance
        TransactionIconType.RESTAURANT -> Icons.Outlined.Restaurant
        TransactionIconType.SHOPPING_BAG -> Icons.Outlined.ShoppingBag
        TransactionIconType.PAYMENTS -> Icons.Outlined.Payments
        TransactionIconType.ADD_CARD -> Icons.Outlined.AddCard
        TransactionIconType.MENU_BOOK -> Icons.Outlined.MenuBook
        TransactionIconType.LOCAL_PRINTSHOP -> Icons.Outlined.LocalPrintshop
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconContainerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconImage,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = transaction.counterpartyName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = transaction.formattedDate,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
                )
            }
        }
        Text(
            text = transaction.amountKes,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = if (transaction.type == TransactionType.CREDIT) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun BudgetSummarySection(
    budgets: List<BudgetUi>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Budgets",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Icon(
                    imageVector = Icons.Outlined.MoreHoriz,
                    contentDescription = "More",
                    tint = MaterialTheme.colorScheme.outline
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                budgets.forEach { budget ->
                    BudgetProgressItem(budget)
                }
            }
        }
    }
}

@Composable
private fun BudgetProgressItem(budget: BudgetUi) {
    val progressColor = when (budget.category) {
        BudgetCategory.FOOD -> MaterialTheme.colorScheme.primary
        BudgetCategory.TRANSPORT -> MaterialTheme.colorScheme.secondary
        BudgetCategory.UTILITIES -> MaterialTheme.colorScheme.tertiary
    }

    val icon = when (budget.category) {
        BudgetCategory.FOOD -> Icons.Outlined.Restaurant
        BudgetCategory.TRANSPORT -> Icons.Outlined.DirectionsBus
        BudgetCategory.UTILITIES -> Icons.Outlined.Bolt
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
            CircularProgressIndicator(
                progress = { budget.percentage },
                modifier = Modifier.size(80.dp),
                color = progressColor,
                strokeWidth = 8.dp,
                trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                strokeCap = StrokeCap.Round
            )
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = progressColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = budget.category.name.lowercase().capitalize(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline
                )
            )
            Text(
                text = "${(budget.percentage * 100).toInt()}%",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}

private fun String.capitalize() = this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
