package com.example.ndugu.feature.transfer.presentation.contactpicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactPickerViewModel : ViewModel() {

    private val _state = MutableStateFlow(ContactPickerState())
    val state = _state.asStateFlow()

    private val _events = Channel<ContactPickerEvent>()
    val events = _events.receiveAsFlow()

    init { loadContacts() }

    fun onAction(action: ContactPickerAction) {
        when (action) {
            is ContactPickerAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
                // TODO: filter contacts by query
            }
            is ContactPickerAction.OnContactSelected -> {
                viewModelScope.launch {
                    _events.send(
                        ContactPickerEvent.NavigateToAmountEntry(
                            phone = action.contact.phone,
                            recipientName = action.contact.displayName,
                        )
                    )
                }
            }
            is ContactPickerAction.OnManualEntryClick -> {
                // Navigate to amount with empty phone — user types it
                viewModelScope.launch {
                    _events.send(ContactPickerEvent.NavigateToAmountEntry(phone = "", recipientName = ""))
                }
            }
            is ContactPickerAction.OnBackClick -> {
                viewModelScope.launch { _events.send(ContactPickerEvent.NavigateBack) }
            }
        }
    }

    private fun loadContacts() {
        // TODO: inject and call contacts sync use case / platform contacts API via interface
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            val frequent = listOf(
                ContactUi("1", "Jordan", true, "https://lh3.googleusercontent.com/aida-public/AB6AXuBkPAL4ZTWVtbw011_rura_-Xxxp4486TrGrXMkNSJ6Egr3LB3LpNTzyIslihcqNFSWskcmWRT4vvM4B5iRFurUVo6OUNcuW0Qexr5I-8DYh20IDGtg2LOzOnfcbhVIDwMcHRM_LxVwH6P7s6VU2G8Splc944ZTg5W3PvBKLoh75f58es_TSceb2PIjjsm1UfrwhKDORD3XZErYqZo3hfYGtM23CQKNrhZ54TWlU0H0yMxsXedKvA7-DDV-MZDY63FppHMj0MA6aqs"),
                ContactUi("2", "Alex M.", true, null),
                ContactUi("3", "Sarah", true, "https://lh3.googleusercontent.com/aida-public/AB6AXuBDcwMmIWSmiefPalc0CTTegeIC6Kohfo74PjF4YisjOP2Cwl3yblXnXNX_Z-sZ9AIQ6BSh5mcajrf68IK4RqCmGPqQUVqh_MMhxtHZU8Wdd6tElhfoQchAuTZKxEGvQjc2c7Cjz6480FgQ8op41f2TFJQ09-zCmFbNXYdHxQCyCvsaHwZQXsdguBMpgL5c9CVNdS5ssd9dlk7TJRIDOdvq3pzAQw644LDyETxcpaGxDPzMY-4W66vkqK7ZZcbBKkBS3WidtxPafOU"),
                ContactUi("4", "Kevin D.", true, null),
                ContactUi("5", "Elena", true, "https://lh3.googleusercontent.com/aida-public/AB6AXuCFkz6CPlS8sI0D2YayxvhRWiLwkbQzPzcgiWsMA0Bd7malad8hmpjhomM09ySHqQNIxj_ibbDLybzsnxYNrSGE4j5IFGLOPHudUYjbNVZVZq6E8vedHaYnRDSA3QpjsuXlf05_pBbT2iDlF_NY7o_R1vegmxOonZgdCv7ImJUdWJamCPuNf2swirPcvrapeaS90fr3DfgAQ_CFOQp5-JMcoWjz8IA1qadNaTQJ-jrJCVsJZ9TkK7avEuiJz900Q29a8UYWldHZ2-8")
            )
            
            val all = listOf(
                ContactUi("+1 (555) 012-3456", "Benjamin Hayes", true),
                ContactUi("+1 (555) 098-7654", "Chloe Davis", true, "https://lh3.googleusercontent.com/aida-public/AB6AXuAOx_w6J_7XOtj5R9Yq3kwS4hsOvBi5zS6GJMOkpdImZ5wpIdU9snzefrPoCzGpYtkb9tlZyhI4ELNKaZ1uOgXpYHV1A8-_Z9vU0yBfybFYqU0hqUCbpiX0fSeFtnyPR2pDVuQCoi41Kvo3QEHDJVNES8HcsfKLHqllTFRDoSMOCyfOHuZ4699WLq5asApVtzxYxa5w5ofio1cL4fLaZM04cbyNDo3aPx_QQiTqmGDAowpUYTbJCq2MBBWao47YHnCvvRdsVP6LiAQ"),
                ContactUi("+1 (555) 234-5678", "Daniel Rivera", true),
                ContactUi("+1 (555) 876-5432", "Isabella Lee", true, "https://lh3.googleusercontent.com/aida-public/AB6AXuB-3bh0qYEKbn601aNinLC29KK_tJoX0H7PE2RkbLuk2hIx2Q_FSvlp3JsOkzuykb5yxWycQ5flrt-QNxDEAuFvtdtOLAEeeJS37hoZrNZ1wJlCebIgp26qaJFFG6WoBK58yp8sfXcCBvbth0sDwrZ129q84NI5pdu217DsoMYFWOm5AJGWnu-k6S1NER4fB2rqMupLzO5BLuQRewb6QfxrHfkzB7eFjWLTzvSCqHoqmvRDz24CwBM7J2AZV6X51lsx_1UGGUA2gis"),
                ContactUi("+1 (555) 345-6789", "Marcus Wright", true)
            )

            _state.update { it.copy(
                frequentContacts = frequent,
                contacts = all,
                isLoading = false
            ) }
        }
    }
}
