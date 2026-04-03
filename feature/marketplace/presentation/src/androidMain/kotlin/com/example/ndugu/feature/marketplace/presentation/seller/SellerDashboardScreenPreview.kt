package com.example.ndugu.feature.marketplace.presentation.seller

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import com.example.ndugu.feature.marketplace.presentation.home.MarketplaceListing

@Preview(showBackground = true)
@Composable
private fun SellerDashboardPreview() {
    CampusWalletTheme {
        SellerDashboardScreen(
            state = SellerDashboardState(
                sellerName = "James K.",
                sellerAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC37EzeTkkwWxVmOI0xjJMEhQ5sFLv0tInVk4qASVi3216zG1FPGynz9h7xChUpBwGHxhef8KKpKb9zWVy9zvhucwye1arfBK16PS6ME5oN7jpZBciwfaCIbuPSruLgCvkZZ7zq5ptL8ex-w5T1yoqOISqwvAQbheoQzjJymGOcVB_vcjIkC_Dp_Jkkb08dug2ZCMxnT-Wq0_u48fPXDk6l0Jauh-oTAR0SZGA1NK9EOq1m8663Cq6iqcgrR_lIWWgj5u-0K1lNlTs",
                totalRevenue = 12450.0,
                pendingOrdersCount = 3,
                activeListingsCount = 8,
                myListings = listOf(
                    MarketplaceListing(
                        id = "1",
                        title = "Nike Air Max Red",
                        price = 4500.0,
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCAwpOIlPPHdsdQY0chqCXUYF312elO6KCD1g3KUAkiQt9As3zETmJU8reo8snCUqDjWkbsT-yWvxX3W8bcfDUZNPkOI1oiwwRwAmRCc2U-RzlrVtthMpAs2pvg09CzKL4dckC4KUq5nWPUSWSH4Z4chq1lEDt0VGoffJSGsKZDNEOw6bOz1p3s3H5_CSdIah6L9M2rIH77Mpx8yze8gydrMCoOFkJiCvvpp1L8pVWmrpdhs0Vh_fGOAaOJeDJCcCnP1dg0vPXMMUI",
                        sellerName = "James K.",
                        sellerAvatarUrl = null,
                        distance = "2km away",
                        isVerified = true
                    ),
                    MarketplaceListing(
                        id = "3",
                        title = "Instax Mini 11",
                        price = 7800.0,
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCXDj30c1rfzndf0pLZRwOGhdaQveWFRuY8P1rI6G9V9dUD2vUn5chP2ZZzI1WZrzE_NQTGNdwfUfOJavr85RHCUCeOkwfSF8v6p26krf6tMGNbv0vOLifX2frslNV_-emZKroCIVO8o_UkxfhKxyObImDDLTGtRYp102mPX5h7T-6sfm8qjXVIARLxqWbcQ9jXbzlxgjHsTKrzLgvBEbxCjGO0_toa_gBaOKh-WbLY0Y38Ewjohxlz82Qcrq9epCeHlJXRuvfssH8",
                        sellerName = "James K.",
                        sellerAvatarUrl = null,
                        distance = "2km away",
                        isVerified = true
                    )
                )
            ),
            onAction = {}
        )
    }
}
