package com.example.ndugu.feature.messaging.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatHistoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(ChatHistoryState())
    val state = _state.asStateFlow()

    private val _events = Channel<ChatHistoryEvent>()
    val events = _events.receiveAsFlow()

    init {
        // Mock Data based on "Stitch" Design
        _state.update { it.copy(
            chats = listOf(
                ChatItem(
                    id = "1",
                    senderName = "Alex Rivera",
                    senderImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA2IMziGj6djFL_0rSE1O21QUvV3J8kr9XVzRhvdo_FBDoxKMwPx9dWq-J9r9aahwnpNOPAbLXqJqAAwaO4jsm2q7xVlKv5NE_9YY-o83DCjHvUEB1ArfI1X2YAt2QL_5kSQeIMQv0oZ88ur8LlXGFR757LDG_NrhHgWHOULQWtTTByCLUnfPvPcicJ3jNnKJIiOawRzq6gBaF-t0d_iclNLvkeb3bo8IZLJW0ogDKW9kaVdwLg0AuHQglgrDLuXlU_f6xTCIcr-Z4",
                    lastMessage = "Is the textbook still available for pickup today?",
                    timestamp = "14:20",
                    unreadCount = 2,
                    isOnline = true,
                    contextItem = ChatContextItem(
                        title = "Advanced Physics Vol 2",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAdMQOYcc_coB29OUP_7aGyBl6VE7qGoUXt9igcB9XFL_U9yQ9eV5ddRZwdg8I3c99qfc-FkeLWTpJxW9rugxQ_3y_uWPvPuxZsD58uuyCfjAzo5PjibZrF-Wje_MBqbIdsYjQdipVgurtCtw_7-3ntGpZ9T9EGvpe88YtHK-I01JcxOnp_mTCHcnB9fav7RfW3jnb9n7mEi12BniGxscwUKhwFU5vjJYYkKvJVoFc4KJeyEqQOypwOFQ-kojrt4jnzjVFAZzpSDZI"
                    )
                ),
                ChatItem(
                    id = "2",
                    senderName = "Jordan Smith",
                    senderImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAbhRjvhIFx_Em_B7YSIIGuUPgxhyjvNd68Lag1Rxsv-SPLmlyVJDLrZLsX5FYIrMd-faTSWC4ilW-dlFNa-4x9FJYqx-CvYAuEvvvKeUI4orkx-_1iQ6cagcR0GD0iMUv0nb93YsUnZtzU97U5tdvei-vMi9yXo5R8kH3Rl26Ky2L818IVDMMt7ndg_QiH5C622-aUPJf3iQROtc0Qa8Jnj7swUX-OdmtEpCnR5Dc5HyhVu9P7yXW-prc0eRRkwFJSiEG0y8jcUss",
                    lastMessage = "That sounds perfect. See you at the Student Union at 5!",
                    timestamp = "Yesterday",
                    contextItem = ChatContextItem(
                        title = "MacBook Pro 2021 Case",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC33LaAC_sYdRHRkTacXQDGMjocgu9xbJWpB9tdt6jcebOIYvJE0s5-Uc1oK6AVgXYNv7CwJHn3mst1p_tK6dnNVFfno9vHCzlacmzYAALa_yTrK2fKMry4Vg0M64usbQ-GX69oW8xZ_I7g6XvWSddz09z_EWvIScJ3Rz3VzIiZ1rvrPVTDpKOJ8a44rGnvUwHh0iqMwhsl6lY2iDjfMTnBFilGQOdadBIazSeE6yDvetJakeFU4xEpd7bD4Ja3-8GBe4Klo4zjdbU"
                    )
                ),
                ChatItem(
                    id = "3",
                    senderName = "Campus Stationery",
                    senderImageUrl = null,
                    lastMessage = "Your custom print order is ready for collection.",
                    timestamp = "Oct 24",
                    deliveryStatus = DeliveryStatus.READ
                ),
                ChatItem(
                    id = "4",
                    senderName = "Maya Chen",
                    senderImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBs1AYCRH5Gb6JnZtoqOBlFA7Pk8J7hVhfCx5JaB4IAGK1TE-Tuik6QiiqA9o5Ok456ANsxL96DfdFAB7wGLY_hkvQWK9wy972yn9CEDajWF_Vq7rrwfdYDmCC62qiKCM4mv-RvFu4MeQdF43k_F67JRPTDmUCi6EJFhCiogNTPVWNtDDvf4IVUuNY_61pme24nQkl-s1o3TGrjSx1mInz0cuo7GYp1ws18-BIdoZRONymD9phOvThon8c7GHka83QdyhgPWqHo9Xg",
                    lastMessage = "Can you do $25 for the calculator? 🧮",
                    timestamp = "09:15",
                    unreadCount = 1,
                    contextItem = ChatContextItem(
                        title = "TI-84 Plus CE",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCaXnSnPQg3tqXVgpnLl07ENc9CxAMH8jW4h2mwoMpww7-P_rjChoKaIoRUepczkURV-FRgxzlWk-fe2XEaZ01u-JjToM86ppE8SWHhAEIHalYaRb73jCQg07NXwvYYRQ58zuDGiy6Fslaooyi9q0BKKp7GP-APD3z6PggtQiXB7DV3yITecert44YbdwrPQ7WjYD_gbdmWuJzyRr4v4t2q2t-jUaPNenZ_NAXwwazSWM0dUl59sC1ACUaWE04z0sh6zFBpKWQq3rs"
                    )
                ),
                ChatItem(
                    id = "5",
                    senderName = "Liam Wilson",
                    senderImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDBu9DTVRS8kMH3nm9vlJgWyrTfpt5bsHxegl2Qb1yOeIeYt4IthcGS1WaetEv5RM-R6BQarPn-aF8IGvxjBurHLH93QoKAC-pqIA5vbbecYa8qsv5aIlcbz6N9w3X9IJ3djMW8ejY-D0VwdJ1tYb0rir98gYOKILcVw7QBYDqM2_ebrnO1lugucyKHc0DHPvbzNs8cptVsrN0DwxRl_VZ5vEjl_niypNYy9vOOgF2E6Wq_Cc77VqFZ-edtY_sOfFNG5B1K5Ee5cY8",
                    lastMessage = "The bike is in great condition, just serviced last month.",
                    timestamp = "Oct 22"
                )
            )
        ) }
    }

    fun onAction(action: ChatHistoryAction) {
        when (action) {
            is ChatHistoryAction.OnBackClick -> {
                viewModelScope.launch { _events.send(ChatHistoryEvent.NavigateBack) }
            }
            is ChatHistoryAction.OnCategorySelect -> {
                _state.update { it.copy(selectedCategory = action.category) }
            }
            is ChatHistoryAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }
            is ChatHistoryAction.OnChatClick -> {
                viewModelScope.launch { _events.send(ChatHistoryEvent.NavigateToChat(action.chatId)) }
            }
            is ChatHistoryAction.OnComposeNewChat -> {
                viewModelScope.launch { _events.send(ChatHistoryEvent.NavigateToCompose) }
            }
        }
    }
}
