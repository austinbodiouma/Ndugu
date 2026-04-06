package com.example.ndugu.feature.wallet.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
private fun ProfileSettingsScreenPreview() {
    CampusWalletTheme {
        ProfileSettingsScreen(
            state = ProfileSettingsState(
                displayName = "Marcus Thorne",
                email = "m.thorne@stanford.edu",
                university = "Stanford University",
                classOf = "2025",
                isVerified = true,
                avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCC19DX9NcbRFELYRQO0Kogve-ZBaK4d0p-mcgdhPAGImF6ssYwH8JrysPDp1vORQEACiyR3zwbZAPXJ9zceRnV8Aoqy_9CCknCG2Ov2GT67sHCXTHgSMunQDVhOfPt7yzbNknANiudJoa21SeCPZALKZ1LO0Juk39mgdpmrztFW1kIkoYxcBe7EzvLlHsMj-bjK2r6X6WnKrYmkaNXBFID1uWddk3TuOcPN4Ko8poyWISu3smmD_lAkrcRDOjP-bAJqsJ53uJ6ew8"
            ),
            onAction = {}
        )
    }
}
