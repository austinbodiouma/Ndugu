package com.example.ndugu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import com.example.ndugu.di.initKoin
import com.example.ndugu.feature.auth.presentation.di.AuthPresentationModule
import com.example.ndugu.feature.marketplace.presentation.di.MarketplacePresentationModule
import com.example.ndugu.feature.messaging.presentation.di.MessagingPresentationModule
import com.example.ndugu.feature.payment.presentation.di.PaymentPresentationModule
import com.example.ndugu.feature.transfer.presentation.di.TransferPresentationModule
import com.example.ndugu.feature.wallet.presentation.di.WalletPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.stopKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Ensure Koin is not already started (e.g., during configuration changes if not handled)
        // Or better, use a custom Application class to init Koin.
        // For now, let's just make sure it's clean if we are re-initializing.
        stopKoin()

        initKoin(
            additionalModules = listOf(
                AuthPresentationModule,
                MarketplacePresentationModule,
                MessagingPresentationModule,
                PaymentPresentationModule,
                TransferPresentationModule,
                WalletPresentationModule
            ),
            appDeclaration = {
                androidContext(this@MainActivity)
                androidLogger()
            }
        )

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
