package com.example.ndugu.feature.transfer.presentation.sendmoney

import com.example.ndugu.core.presentation.UiText

data class SendMoneyState(
    val recipientPhone: String = "",
    val recipientName: String = "",
    val recipientAvatarUrl: String? = null,
    val amountKes: String = "",
    val memo: String = "",
    val availableBalanceKes: String = "",
    val isLoading: Boolean = false,
    val amountError: UiText? = null,
    val isConfirmStep: Boolean = false,
)

sealed interface SendMoneyAction {
    data class OnAmountChange(val amount: String) : SendMoneyAction
    data class OnMemoChange(val memo: String) : SendMoneyAction
    data object OnReviewClick : SendMoneyAction
    data object OnConfirmSendClick : SendMoneyAction
    data object OnEditClick : SendMoneyAction
    data object OnBackClick : SendMoneyAction
}

sealed interface SendMoneyEvent {
    data class NavigateToSuccess(val transactionId: String) : SendMoneyEvent
    data object NavigateBack : SendMoneyEvent
    data class ShowSnackbar(val message: UiText) : SendMoneyEvent
}
