package com.example.ndugu.feature.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginRoot(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LoginEvent.NavigateToHome -> onNavigateToHome()
            is LoginEvent.NavigateToRegister -> onNavigateToRegister()
            is LoginEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    LoginScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Sign in to your CampusWallet",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(40.dp))

        // TODO: Replace with CWTextField — Phone and Password fields

        Spacer(Modifier.height(24.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { onAction(LoginAction.OnLoginClick) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Sign In")
            }
            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = { onAction(LoginAction.OnBiometricLoginClick) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Use Biometrics")
            }
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = { onAction(LoginAction.OnRegisterClick) }) {
            Text("Don't have an account? Register")
        }
    }
}
