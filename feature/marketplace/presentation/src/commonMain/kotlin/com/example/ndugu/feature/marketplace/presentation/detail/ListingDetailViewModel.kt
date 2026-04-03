package com.example.ndugu.feature.marketplace.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListingDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(ListingDetailState(
        listing = ListingDetail(
            id = "1",
            title = "Handcrafted Full-Grain Leather Laptop Sleeve",
            price = 850.0,
            originalPrice = 1200.0,
            category = "Electronics & Tech",
            rating = 4.8f,
            reviewCount = 12,
            images = listOf(
                "https://lh3.googleusercontent.com/aida-public/AB6AXuD4nQGv9Q9LCkf4XPKI4PgRW7R9qfNNNRgyRn2R7-xvfz2ryXCHlUTgob6Fm0FTPOuCTOqtnouARdbNudBpLcO1wT4zV7Nt5Rsd5ApNUjHivCgKQZGxQUERiX76n6_o5icNch3Bvi1BmBPkRiaVBNDUm7TVt6lRU6sPPxhLldqDv-Y0_GsRQfs0F3_CMzqQsxLvwK6eRh20YnNA2LBc2LdlfUplSHB_ecTGMS_utiZi06Oc0VMLxKWdh2A7QqqTOucOAXqsDty6WIE",
                "https://lh3.googleusercontent.com/aida-public/AB6AXuABmQkTDjlJok8KQZlahJ9eAmTsyCkwJzyjKxrilvpMpU3HhR9qBLQTDH_RpOigQ2JRRSddJFykg8YGWg8bsC-B1A4dN8JwZCp54ko7IzfA9Sq3IXRP_inSIXYsk33lImiMHaBozyLkO_reePAXeGhzioFpdusYt6dxUBlGk8KFWn4AztZpikcyUOFEBwonkv4YETe_HoleF114C4fSYteZqNRY_n4o2Pf2bvISeEZ53qTcXW0u3jJ_UIRlqEVtWEy9cOaHqqpHVOA",
                "https://lh3.googleusercontent.com/aida-public/AB6AXuD59Eo3jUt2WHUX18ULRmLj_uKdgNO0_jgAac_VukvuX5dvn2Dd7qDuVrTCbSUNkyVlEnSOLoPYCNAsUmYs0_4-i3Z65imaUro6bYMKfqopa_MPy0aIJlCpWzS7gfLPaFTaKYUHrqwU6Y5016m1SE7aDU_TeqfnuaDMakK0BrybVTMjtKQcFBYJ2RcaTwbqUiQ_eYpoqSyf2tCO2CYi7u8f8I_BQMAwlmkGgOQ3_Nsz4PAMYpp7lFN_H0juKLMDf9GRVXh0-65kgXU"
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
                    id = "R1",
                    userName = "Sarah Jane",
                    userInitials = "SJ",
                    rating = 5,
                    comment = "The quality exceeded my expectations. It feels very premium and the leather scent is amazing. Fits my MacBook Pro 14 like a glove!",
                    date = "2 days ago",
                    avatarColor = 0xFFE1DFFF
                ),
                Review(
                    id = "R2",
                    userName = "Mike W.",
                    userInitials = "MW",
                    rating = 4,
                    comment = "Great product. Alex was very responsive and we met up at the library within an hour of my purchase. Highly recommended seller.",
                    date = "1 week ago",
                    avatarColor = 0xFFA2F0EF
                )
            )
        )
    ))
    val state = _state.asStateFlow()

    private val _events = Channel<ListingDetailEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ListingDetailAction) {
        when (action) {
            is ListingDetailAction.OnBackClick -> {
                viewModelScope.launch { _events.send(ListingDetailEvent.NavigateBack) }
            }
            ListingDetailAction.OnShareClick -> {
                // TODO: Implement share
            }
            ListingDetailAction.OnFavoriteClick -> {
                // TODO: Implement favorite
            }
            ListingDetailAction.OnMessageSellerClick -> {
                // TODO: Implement message seller
            }
            ListingDetailAction.OnAddToCartClick -> {
                // TODO: Implement add to cart
            }
            ListingDetailAction.OnBuyNowClick -> {
                // TODO: Implement buy now
            }
        }
    }
}

