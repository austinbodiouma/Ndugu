package com.example.ndugu.feature.marketplace.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketplaceHomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(MarketplaceHomeState())
    val state = _state.asStateFlow()

    private val _events = Channel<MarketplaceHomeEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadMockData()
    }

    private fun loadMockData() {
        _state.update {
            it.copy(
                categories = listOf("All", "Fashion", "Food", "Books", "Electronics", "Sports"),
                selectedCategory = "All",
                userAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBKJCroi2R3INgaE-_bIsXSILuzuc79TrxDOT3nDI23CyBRTKWqXLeDw4s8MkXb3poaqECBhyYoIrJVtDpRuW0pJew2Wnay-6MAm2xkNoKfTgBDR5n1PEvFupS-rImDcnJRcLtj2M5T0pz9SkeMyilE7EQgAhG2O5_Jeym0W4wPiiIFaLNs3U-57cV92Blc9ygmoRoApdGqoLRrtgzDalHIDIVWTGNpQr5AZtj5BjAHHtLQXE7iGlRS8pBWpRrS1zcnodRx0W4GoEc",
                featuredListing = FeaturedListing(
                    id = "feat_1",
                    title = "Dorm Life Essentials",
                    subtitle = "Up to 40% off for verified students",
                    tag = "CAMPUS EXCLUSIVE",
                    imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDxoDe3q4Pb6sR7y1GIWnBwXM9zi18Y6fhWVL9Ix1Vw0vx0FW5-Uza2WRAaAu9QNIqcZJZt5jwJezfbdsZSxDgUZBXn2dEUOCv9VUGGugBtlruEdfu_mEuy6BbwseS3Cg82XkvsGgPwQUIo3fIdjF-DMKSIKrvxr80lvBhNkKzbaTtjs_4qjTawOjLZr4ucobwFmwx15unAnVwe_eKxEbIRQr4FKhlzfcslzfuGkznNDorn0SuXJoXwa-uocXwULHsSHbgdi8qJ0XQ"
                ),
                listings = listOf(
                    MarketplaceListing(
                        id = "1",
                        title = "Nike Air Max Red",
                        price = 4500.0,
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCAwpOIlPPHdsdQY0chqCXUYF312elO6KCD1g3KUAkiQt9As3zETmJU8reo8snCUqDjWkbsT-yWvxX3W8bcfDUZNPkOi1oiwwRwAmRCc2U-RzlrVtthMpAs2pvg09CzKL4dckC4KUq5nWPUSWSH4Z4chq1lEDt0VGoffJSGsKZDNEOw6bOz1p3s3H5_CSdIah6L9M2rIH77Mpx8yze8gydrMCoOFkJiCvvpp1L8pVWmrpdhs0Vh_fGOAaOJeDJCcCnP1dg0vPXMMUI",
                        sellerName = "James K.",
                        sellerAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC37EzeTkkwWxVmOI0xjJMEhQ5sFLv0tInVk4qASVi3216zG1FPGynz9h7xChUpBwGHxhef8KKpKb9zWVy9zvhucwye1arfBK16PS6ME5oN7jpZBciwfaCIbuPSruLgCvkZZ7zq5ptL8ex-w5T1yoqOISqwvAQbheoQzjJymGOcVB_vcjIkC_Dp_Jkkb08dug2ZCMxnT-Wq0_u48fPXDk6l0Jauh-oTAR0SZGA1NK9EOq1m8663Cq6iqcgrR_lIWWgj5u-0K1lNlTs",
                        distance = "2km away",
                        isVerified = true
                    ),
                    MarketplaceListing(
                        id = "2",
                        title = "Calculus Bundle",
                        price = 2200.0,
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDDaEzncjIQxxIAF3q05H15K-YX_w5a4HhCqiMt6O6KKlrUqNcD_W_cDeM-502Ga7CV1UhQFsip9pnEHf1pvVME0vmNzXVcysCZNgNRvVJRqMbOC_OQtfaedoGZN2X-QgYqk7xbgT0ahu-VMC5TpF5Fkd76qsRHG_9TPpDJtH6_2zg63s5Oxcwn5MMVbpvzgztNdr-HhJFMTcumlxiyS0q7gjDAnc3FPMgiZipCwKEK80hE4Y5lg3HXHhpuPht061YYq9ZN1JZiyTI",
                        sellerName = "Sarah M.",
                        sellerAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBNJIeZX7oKfU4dUv9HePTl3193hxvUrHZKHmgREok36QuGFrxyFJ6CvheiAMkYg2mdrfPCC6J1UqjCaYd3POKCxUGnYxLm-Cuv1wzIQvpuhaSKEtMnrFSD8uvF-6nn2iL8GwfhAHSrr-1XFXkfwpYVWv73jXW00qHngbJiPnL218wdFPn6jSZTn-6VeMbD5f0Ho-8qQM_9ZWOq5STOy9TH5lWfgJrU52TxZbjsvcYU0TbwPoNBdPtIFgo3BgQTp7X5o9g15hfQY1M",
                        distance = "500m away",
                        isVerified = false
                    ),
                    MarketplaceListing(
                        id = "3",
                        title = "Instax Mini 11",
                        price = 7800.0,
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCXDj30c1rfzndf0pLZRwOGhdaQveWFRuY8P1rI6G9V9dUD2vUn5chP2ZZzI1WZrzE_NQTGNdwfUfOJavr85RHCUCeOkwfSF8v6p26krf6tMGNbv0vOLifX2frslNV_-emZKroCIVO8o_UkxfhKxyObImDDLTGtRYp102mPX5h7T-6sfm8qjXVIARLxqWbcQ9jXbzlxgjHsTKrzLgvBEbxCjGO0_toa_gBaOKh-WbLY0Y38Ewjohxlz82Qcrq9epCeHlJXRuvfssH8",
                        sellerName = "David O.",
                        sellerAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB5HslFN2nycH8_mwtAxkTMvAVjd5s867yxgDSkyH3V2xCwfPUE4zANtVe6TVBk3tQEji_OLOGzrz1y8m93nMPOIS0HGFhhHKThBQImXxXdesqsoDenhM9N-_gduNoTE6r_G6xndhrLuofCr7qlpfVqjQT4sBn4U0ptW467UYauGpWDcSBEFqbonpq5P0Sks2qXzxNNv4OXp4b5pgk5WAij_JKAnrx4GjSVgJRYZpHbK67IMS5Wa58V5rQnWrT6Cx_MY_iWQ9xlE_A",
                        distance = "1km away",
                        isVerified = true
                    ),
                    MarketplaceListing(
                        id = "4",
                        title = "Silicone Case Pro",
                        price = 850.0,
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCZkT9LBivWZxAN654Jgm5JlGXsA-veitskIRWAep31gpHuHFR47_1zXmZYtKBedU8YKgxgDy1o0Xs2eHdQZ7mXEtDqVmXIWCJX_J8ZyNO83mVFcpyCIRlwFGB1OXnixCQ8OBMDgDpdUunvb5oMk61Nal2woFO9pPyGrOEoa2ylfeqEeK9zCeUrzZ4QANhIKrBPbgbTB0h6gN3d50HVYJR4xGZLYeeMVEoQfH0c0DPrNVKbdvn1Cdm8KHX5J-GDKunH-zt3R4OTYaA",
                        sellerName = "Elena R.",
                        sellerAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDlXCQZ5nOnunf-69njha4AHGFpZDhu4l_O7FW98jbckIJIXUZHDeVCEdL-SEcDp2wxJYkcLa1U05iftPOlcgtCokERQ5z1Y3QC0e7yjXWusv3TQJ0XNFLwas9RPrd2Ef2hSt9bw0JEQNECvlJae8dVDcmO-vOKoSyDRiuPAHBRboUmK39ZcVXwClZnNZR6t5hlllTqr_ewtx-g9V0jxBmyr5BuIi-MZPbcJ4XRFRBtgenwhGtcUv8R7IqlqnLJneo-O5ynfSrDn_k",
                        distance = "3km away",
                        isVerified = false
                    )
                )
            )
        }
    }

    fun onAction(action: MarketplaceHomeAction) {
        when (action) {
            is MarketplaceHomeAction.OnBackClick -> {
                viewModelScope.launch { _events.send(MarketplaceHomeEvent.NavigateBack) }
            }
            is MarketplaceHomeAction.OnCategoryClick -> {
                _state.update { it.copy(selectedCategory = action.category) }
            }
            is MarketplaceHomeAction.OnListingClick -> {
                viewModelScope.launch { _events.send(MarketplaceHomeEvent.NavigateToListingDetail(action.listingId)) }
            }
            MarketplaceHomeAction.OnSearchClick -> {
                viewModelScope.launch { _events.send(MarketplaceHomeEvent.NavigateToSearch) }
            }
            MarketplaceHomeAction.OnSellClick -> {
                viewModelScope.launch { _events.send(MarketplaceHomeEvent.NavigateToCreateListing) }
            }
            is MarketplaceHomeAction.OnToggleFavorite -> {
                _state.update { currentState ->
                    currentState.copy(
                        listings = currentState.listings.map { listing ->
                            if (listing.id == action.listingId) {
                                listing.copy(isFavorite = !listing.isFavorite)
                            } else listing
                        }
                    )
                }
            }
        }
    }
}
