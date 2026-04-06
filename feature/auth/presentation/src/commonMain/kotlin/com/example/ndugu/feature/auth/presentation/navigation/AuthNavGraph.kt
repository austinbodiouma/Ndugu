package com.example.ndugu.feature.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.ndugu.feature.auth.presentation.login.LoginRoot
import com.example.ndugu.feature.auth.presentation.otp.OtpVerifyRoot
import com.example.ndugu.feature.auth.presentation.register.RegisterRoot
import com.example.ndugu.feature.auth.presentation.splash.SplashRoot
import com.example.ndugu.feature.auth.presentation.uploadid.UploadIdRoot
import com.example.ndugu.feature.auth.presentation.verificationpending.VerificationPendingRoot
import com.example.ndugu.feature.auth.presentation.welcome.WelcomeRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onNavigateToHome: () -> Unit,
) {
    navigation<SplashRoute>(startDestination = SplashRoute) {
        composable<SplashRoute> {
            SplashRoot(
                onSplashFinished = {
                    navController.navigate(WelcomeRoute) {
                        popUpTo<SplashRoute> { inclusive = true }
                    }
                },
            )
        }
        composable<WelcomeRoute> {
            WelcomeRoot(
                onNavigateToLogin = {
                    navController.navigate(LoginRoute)
                },
                onNavigateToRegister = {
                    navController.navigate(RegisterRoute)
                },
            )
        }
        composable<LoginRoute> {
            LoginRoot(
                onNavigateToHome = onNavigateToHome,
                onNavigateToRegister = { navController.navigate(RegisterRoute) },
            )
        }
        composable<RegisterRoute> {
            RegisterRoot(
                onNavigateToOtpVerify = { phone ->
                    navController.navigate(OtpVerifyRoute(phone = phone))
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                },
            )
        }
        composable<OtpVerifyRoute> { backStackEntry ->
            val route: OtpVerifyRoute = backStackEntry.toRoute()
            OtpVerifyRoot(
                onNavigateToUploadId = { navController.navigate(UploadIdRoute) },
            )
        }
        composable<UploadIdRoute> {
            UploadIdRoot(
                onNavigateToHome = onNavigateToHome,
            )
        }
        composable<VerificationPendingRoute> {
            VerificationPendingRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = onNavigateToHome,
            )
        }
    }
}

