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
                }}
            )
        }}
    ) {{ padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {{
            Text("{ScreenTitle} Screen Content")
        }}
    }}
}}
"""

routes_template = """package com.example.ndugu.feature.{feature}.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface {FeatureName}Route {{
{routes}
}}
"""

navgraph_template = """package com.example.ndugu.feature.{feature}.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
{imports}

fun NavGraphBuilder.{feature}Graph(
    navController: NavController,
) {{
    navigation<{StartRoute}>(startDestination = {StartRoute}) {{
{composables}
    }}
}}
"""

for feature, screens in features.items():
    feat_name_cap = feature.capitalize()
    presentation_dir = os.path.join(base_dir, feature, "presentation", "src", "commonMain", "kotlin", "com", "example", "ndugu", "feature", feature, "presentation")
    
    # Navigation dirs
    nav_dir = os.path.join(presentation_dir, "navigation")
    os.makedirs(nav_dir, exist_ok=True)
    
    # Create Routes
    routes_str = ""
    for screen_pkg, screen_name, screen_title in screens:
        routes_str += f"    @Serializable\n    data object {screen_name}Route : {feat_name_cap}Route\n\n"
    
    with open(os.path.join(nav_dir, f"{feat_name_cap}Routes.kt"), 'w', encoding='utf-8') as f:
        f.write(routes_template.format(feature=feature, FeatureName=feat_name_cap, routes=routes_str.strip()))
        
    # Create NavGraph
    imports_str = ""
    composables_str = ""
    for screen_pkg, screen_name, screen_title in screens:
        imports_str += f"import com.example.ndugu.feature.{feature}.presentation.{screen_pkg}.{screen_name}Root\n"
        composables_str += f"        composable<{screen_name}Route> {{\n            {screen_name}Root(\n                onNavigateBack = {{ navController.popBackStack() }}\n            )\n        }}\n"
        
    with open(os.path.join(nav_dir, f"{feat_name_cap}NavGraph.kt"), 'w', encoding='utf-8') as f:
        f.write(navgraph_template.format(
            feature=feature, 
            imports=imports_str.strip(), 
            StartRoute=f"{screens[0][1]}Route",
            composables=composables_str.strip()
        ))

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

print("Presentation scaffolding complete.")
