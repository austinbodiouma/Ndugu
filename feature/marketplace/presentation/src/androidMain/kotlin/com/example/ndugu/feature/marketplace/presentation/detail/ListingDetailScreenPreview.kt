package com.example.ndugu.feature.marketplace.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
private fun ListingDetailPreview() {
    CampusWalletTheme {
        ListingDetailScreen(
            state = ListingDetailState(
                listing = ListingDetail(
                    id = "1",
                    title = "Handcrafted Full-Grain Leather Laptop Sleeve",
                    price = 850.0,
                    originalPrice = 1200.0,
                    currency = "KES",
                    category = "Electronics & Tech",
                    rating = 4.8f,
                    reviewCount = 12,
                    images = listOf(
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuD4nQGv9Q9LCkf4XPKI4PgRW7R9qfNNNRgyRn2R7-xvfz2ryXCHlUTgob6Fm0FTPOuCTOqtnouARdbNudBpLcO1wT4zV7Nt5Rsd5ApNUjHivCgKQZGxQUERiX76n6_o5icNch3Bvi1BmBPkRiaVBNDUm7TVt6lRU6sPPxhLldqDv-Y0_GsRQfs0F3_CMzqQsxLvwK6eRh20YnNA2LBc2LdlfUplSHB_ecTGMS_utiZi06Oc0VMLxKWdh2A7QqqTOucOAXqsDty6WIE"
                    ),
                    seller = SellerInfo(
                        name = "Alex Kamau",
                        avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDQ4xYvnRSzpN5zUadIU8hhx0yJGXLwg_MdTT5OUbWU07hdlfL61bWhp341DPm3AJ4MwpyJot6CUD9t6cT0Ja2rCjvwaanvNztMiPf6U35uiixsk1a4w2-e-SP7SS8y-F7NCwdtbkTUcD9gqVsFNnZ7rmCqfsNI4jIf0qekfSFkn1WSjsn-E0wTzOyeu-vrIvbv68ON2m7WzUCTEQVd8f0df30yUGBilgjdLPcYeJbcv60n0ae0xfPt-JZu3eUrAq1o9qfz7DjMZZk",
                        isVerified = true,
                        role = "Trusted Student Seller"
                    ),
                    description = "Elevate your everyday carry with this premium, handcrafted laptop sleeve. Designed for students who value both style and protection, this sleeve features a water-resistant exterior and a plush micro-fiber lining to prevent scratches.",
                    features = listOf(
                        "100% Genuine Leather",
                        "Fits up to 14\" Laptops",
                        "Internal Accessory Pocket",
                        "Slim Profile Design"
                    ),
                    deliveryInfo = "Same-day pickup at Student Center",
                    returnPolicy = "If the fit isn't perfect for your tech",
                    securityInfo = "Secure campus transactions only",
                    reviews = listOf(
                        Review(
                            id = "r1",
                            userInitials = "SJ",
                            userName = "Sarah Jane",
                            date = "2 days ago",
                            rating = 5,
                            comment = "The quality exceeded my expectations. It feels very premium and the leather scent is amazing. Fits my MacBook Pro 14 like a glove!",
                            avatarColor = 0xFFC1C1FF
                        ),
                        Review(
                            id = "r2",
                            userInitials = "MW",
                            userName = "Mike W.",
                            date = "1 week ago",
                            rating = 4,
                            comment = "Great product. Alex was very responsive and we met up at the library within an hour of my purchase. Highly recommended seller.",
                            avatarColor = 0xFFA2F0EF
                        )
                    )
                )
            ),
            onAction = {}
        )
    }
}
