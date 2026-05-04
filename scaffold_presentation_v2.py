import os

base_dir = r"d:\Android\Projects\Ndugu\feature"

features = {
    "marketplace": [
        ("home", "MarketplaceHome", "Marketplace"),
        ("detail", "ListingDetail", "Listing Details"),
        ("create", "CreateListing", "Create Listing"),
        ("seller", "SellerDashboard", "Seller Dashboard"),
        ("dispute", "Dispute", "Dispute Transaction")
    ],
    "messaging": [
        ("history", "ChatHistory", "Messages"),
        ("room", "ChatRoom", "Chat")
    ],
    "payment": [
        ("scanner", "QRScanner", "Scan QR Code"),
        ("confirmation", "QRPaymentConfirmation", "Confirm Payment"),
        ("topup", "TopUp", "Top Up Wallet")
    ]
}

state_template = """package com.example.ndugu.feature.{feature}.presentation.{screen_pkg}

import com.example.ndugu.core.presentation.UiText

data class {ScreenName}State(
    val isLoading: Boolean = false,
    val error: UiText? = null
)

sealed interface {ScreenName}Action {{
    data object OnBackClick : {ScreenName}Action
}}

sealed interface {ScreenName}Event {{
    data object NavigateBack : {ScreenName}Event
}}
"""

vm_template = """package com.example.ndugu.feature.{feature}.presentation.{screen_pkg}

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class {ScreenName}ViewModel : ViewModel() {{

    private val _state = MutableStateFlow({ScreenName}State())
    val state = _state.asStateFlow()

    private val _events = Channel<{ScreenName}Event>()
    val events = _events.receiveAsFlow()

    fun onAction(action: {ScreenName}Action) {{
        when (action) {{
            is {ScreenName}Action.OnBackClick -> {{
                viewModelScope.launch {{ _events.send({ScreenName}Event.NavigateBack) }}
            }}
        }}
    }}
}}
"""

screen_template = """package com.example.ndugu.feature.{feature}.presentation.{screen_pkg}

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun {ScreenName}Root(
    onNavigateBack: () -> Unit,
    viewModel: {ScreenName}ViewModel = koinViewModel(),
) {{
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) {{ event ->
        when (event) {{
            is {ScreenName}Event.NavigateBack -> onNavigateBack()
        }}
    }}

    {ScreenName}Screen(
        state = state,
        onAction = viewModel::onAction,
    )
}}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun {ScreenName}Screen(
    state: {ScreenName}State,
    onAction: ({ScreenName}Action) -> Unit,
) {{
    CampusWalletTheme {{
        Scaffold(
            topBar = {{
                TopAppBar(
                    title = {{ Text("{ScreenTitle}") }},
                    navigationIcon = {{
                        IconButton(onClick = {{ onAction({ScreenName}Action.OnBackClick) }}) {{
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }}
                    }},
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }},
            containerColor = MaterialTheme.colorScheme.background
        ) {{ padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {{
                if (state.isLoading) {{
                    CircularProgressIndicator()
                }} else {{
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {{
                        Text(
                            text = "{ScreenTitle} Screen Content",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        // Sample Premium Placeholder
                        Surface(
                            modifier = Modifier.size(120.dp),
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shadowElevation = 4.dp
                        ) {{
                            Box(contentAlignment = Alignment.Center) {{
                                Text("Premium Card", style = MaterialTheme.typography.labelSmall)
                            }}
                        }}
                    }}
                }}
            }}
        }}
    }}
}}

@Preview
@Composable
fun {ScreenName}Preview() {{
    {ScreenName}Screen(
        state = {ScreenName}State(),
        onAction = {{}}
    )
}}
"""

di_template = """package com.example.ndugu.feature.{feature}.presentation.di

import com.example.ndugu.feature.{feature}.presentation.{screen_pkgs}
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val {feat_name_cap}PresentationModule = module {{
{viewmodels}
}}
"""

for feature, screens in features.items():
    feat_name_cap = feature.capitalize()
    presentation_dir = os.path.join(base_dir, feature, "presentation", "src", "commonMain", "kotlin", "com", "example", "ndugu", "feature", feature, "presentation")
    
    # DI dir
    di_dir = os.path.join(presentation_dir, "di")
    os.makedirs(di_dir, exist_ok=True)
    
    # Create DI Module
    vm_str = ""
    for screen_pkg, screen_name, screen_title in screens:
        vm_str += f"    viewModelOf(::{screen_name}ViewModel)\n"
    
    # We need to import all viewmodels. Simplest is to import the packages or individual ones.
    import_vms = ""
    for screen_pkg, screen_name, screen_title in screens:
        import_vms += f"import com.example.ndugu.feature.{feature}.presentation.{screen_pkg}.{screen_name}ViewModel\n"

    with open(os.path.join(di_dir, f"{feat_name_cap}PresentationModule.kt"), 'w', encoding='utf-8') as f:
        f.write(di_template.format(
            feature=feature, 
            feat_name_cap=feat_name_cap, 
            screen_pkgs=screens[0][0], # Placeholder for imports
            viewmodels=vm_str.strip()
        ).replace("import com.example.ndugu.feature.{feature}.presentation.{screen_pkgs}", import_vms.strip()))

    # Create Screen, VM, State
    for screen_pkg, screen_name, screen_title in screens:
        pkg_dir = os.path.join(presentation_dir, screen_pkg)
        os.makedirs(pkg_dir, exist_ok=True)
        
        # State
        with open(os.path.join(pkg_dir, f"{screen_name}State.kt"), 'w', encoding='utf-8') as f:
            f.write(state_template.format(feature=feature, screen_pkg=screen_pkg, ScreenName=screen_name))
            
        # VM
        with open(os.path.join(pkg_dir, f"{screen_name}ViewModel.kt"), 'w', encoding='utf-8') as f:
            f.write(vm_template.format(feature=feature, screen_pkg=screen_pkg, ScreenName=screen_name))
            
        # Screen
        with open(os.path.join(pkg_dir, f"{screen_name}Screen.kt"), 'w', encoding='utf-8') as f:
            f.write(screen_template.format(feature=feature, screen_pkg=screen_pkg, ScreenName=screen_name, ScreenTitle=screen_title))

print("Feature fleshing out complete.")
