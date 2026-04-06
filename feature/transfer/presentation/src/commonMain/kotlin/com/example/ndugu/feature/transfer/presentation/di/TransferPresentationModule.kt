package com.example.ndugu.feature.transfer.presentation.di

import com.example.ndugu.feature.transfer.presentation.contactpicker.ContactPickerViewModel
import com.example.ndugu.feature.transfer.presentation.reversal.ReversalViewModel
import com.example.ndugu.feature.transfer.presentation.sendmoney.SendMoneyViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val TransferPresentationModule = module {
    viewModelOf(::ContactPickerViewModel)
    viewModelOf(::SendMoneyViewModel)
    viewModelOf(::ReversalViewModel)
}
