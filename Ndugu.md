# 📱 CampusWallet — Project Planning Document

> **Version:** 1.0  
> **Author:** Discovery Interview — Senior KMP Engineer  
> **Platform:** Android & iOS (Kotlin Multiplatform Mobile)  
> **Status:** Pre-Development Planning

---

## Table of Contents

1. [📋 Project Overview](#-project-overview)
2. [🎯 Features & User Stories](#-features--user-stories)
3. [🏗️ Technical Architecture (KMP)](#-technical-architecture-kmp)
4. [🔐 Security & Compliance](#-security--compliance)
5. [📊 Admin & Operations](#-admin--operations)
6. [🔔 Notifications Strategy](#-notifications-strategy)
7. [🚀 Phased Delivery Roadmap](#-phased-delivery-roadmap)

---

## 📋 Project Overview

### App Name
**CampusWallet** *(working title)*

### Elevator Pitch
CampusWallet is a student-first super-app combining a mobile e-wallet with a peer-to-peer campus marketplace. Students save, send, and spend money — all within a trusted network of verified campus peers and local partner businesses.

### Problem Statement
University students in Kenya face a fragmented financial and commercial experience:
- Campus rules often prohibit on-site physical trading, cutting off student entrepreneurs from their most accessible customer base.
- Students have no reliable way to save and manage money sent from home between academic terms.
- There is no trusted, student-verified platform to buy and sell goods within a campus community.
- Sending money peer-to-peer requires third-party apps that lack campus context, contact discovery, or dispute protection.

CampusWallet solves all of these in a single, student-verified ecosystem.

### Target Audience
**Primary:** Kenyan university students aged 18–26  
**Secondary:** Local businesses adjacent to university campuses (cafeterias, supermarkets, stationery shops)  
**Launch Campus:** Single university (approx. 10,000 students) as pilot

### Success Metrics
| Metric | Target (End of Year 1) |
|---|---|
| Registered & verified students | 3,000+ |
| Monthly active users (MAU) | 1,500+ |
| Total wallet transactions processed | KES 1,000,000+ |
| Active marketplace listings | 500+ |
| Partner businesses onboarded | 5+ |
| Dispute resolution rate | >90% resolved within 48hrs |
| App store rating | 4.2+ stars |

---

## 🎯 Features & User Stories

### 🔴 Core Features (Must Ship)

---

#### 1. Student Onboarding & Verification

**User Stories:**
- As a student, I want to register with my phone number, email, and student ID so that I can access a trusted, student-only platform.
- As a student, I want to use the wallet immediately after sign-up while my student ID is being reviewed, so that I'm not blocked from basic functionality.
- As a student, I want my phone number to be my unique identifier so that friends can find and send me money easily.

**Acceptance Criteria:**
- [ ] Registration requires: phone number, email, password, and student ID photo upload.
- [ ] Phone number is validated via SMS OTP before account is created.
- [ ] User gets immediate access to wallet (receive money, check balance) post-registration.
- [ ] Marketplace buying/selling is locked until admin approves student ID.
- [ ] Verification status is clearly communicated in the UI (e.g., "Pending Verification" badge).
- [ ] Biometric authentication (fingerprint/Face ID) is available as a login option after first sign-in.

---

#### 2. E-Wallet

**User Stories:**
- As a student, I want to see my current wallet balance and recent transactions on my home screen so that I always know where I stand financially.
- As a student, I want to top up my wallet via M-Pesa Paybill so that family members at home can send me money easily.
- As a student, I want to set a spending budget per category (e.g., food, transport) so that I can manage my campus expenses responsibly.
- As a student, I want to receive a push notification when my balance drops below a threshold I set so that I'm never caught off-guard.

**Acceptance Criteria:**
- [ ] Wallet home screen displays: current balance, quick-action buttons (Top Up, Send Money), and a list of the last 20 transactions.
- [ ] Top-up is done via M-Pesa Paybill; the student's registered phone number is the account number.
- [ ] Successful M-Pesa deposit reflects in wallet balance within 30 seconds via webhook.
- [ ] Transaction history shows: date, type (credit/debit), amount, counterparty name/business, and memo/note.
- [ ] Budget categories are customisable; progress bars show spend vs. budget.
- [ ] Low balance alert threshold is configurable by the user.
- [ ] All wallet amounts are displayed in KES.

---

#### 3. Peer-to-Peer Money Transfers

**User Stories:**
- As a student, I want to send money to another student using their phone number so that I can pay friends back easily.
- As a student, I want to see which of my phone contacts are already on CampusWallet so that I can find friends without memorising numbers.
- As a student, I want to add a note to a transfer so that the recipient knows what it's for.
- As a student, I want a clear confirmation screen before money leaves my wallet so that I don't send money to the wrong person.
- As a student, I want to request a reversal if I send money to the wrong person so that I have recourse for mistakes.

**Acceptance Criteria:**
- [ ] Contact list is synced to show which contacts are CampusWallet users (requires device contacts permission).
- [ ] Fallback to manual phone number entry if recipient is not in contacts.
- [ ] Recipient's name and profile picture (if set) are displayed on confirmation screen before sending.
- [ ] Optional memo/note field (max 100 characters) on transfer screen.
- [ ] Transfer is instant and reflected in both sender and recipient wallets in real time.
- [ ] Reversal requests can be initiated within 30 minutes of a transfer.
- [ ] Reversal requires recipient approval; both parties are notified of request and outcome.

---

#### 4. Student Marketplace

##### 4a. Seller — Listing Management

**User Stories:**
- As a student seller, I want to list items for sale with photos, a description, price, category, and stock quantity so that buyers can find and understand what I'm offering.
- As a student seller, I want a seller dashboard so that I can track my inventory, active listings, orders, and total earnings.
- As a student seller, I want to mark an order as "delivered" in the app so that escrow funds are released to me.

**Acceptance Criteria:**
- [ ] Item listing requires: title, category (Fashion, Food/Snacks, Books, Cutlery, Electronics, Other), description, price (KES), stock quantity, and at least 1 photo (max 5).
- [ ] Listings are only visible on marketplace after student ID is verified.
- [ ] Seller dashboard shows: active listings, pending orders, completed orders, total revenue, and items low in stock.
- [ ] Stock quantity auto-decrements when an order is confirmed.
- [ ] Seller receives real-time notification when a new order is placed.
- [ ] Seller must tap "Confirm Delivery" for escrow release to be triggered.

##### 4b. Buyer — Browsing & Purchasing

**User Stories:**
- As a student buyer, I want to browse the marketplace as a feed of trending/popular items so that I discover what's selling on campus.
- As a student buyer, I want to filter by category and search by item name so that I can find specific things quickly.
- As a student buyer, I want to buy an item with my wallet balance, with funds held in escrow, so that my money is protected until I receive the goods.
- As a student buyer, I want to confirm receipt of an item in the app, or raise a dispute with photo evidence, so that I have protection against bad sellers.

**Acceptance Criteria:**
- [ ] Marketplace home tab shows a grid/feed of listings sorted by popularity (order volume).
- [ ] Category filter chips are visible at the top of the feed.
- [ ] Search bar supports full-text search on item title and description.
- [ ] Buyer taps "Buy Now" → confirmation screen (item details, price, seller info) → payment from wallet → escrow hold.
- [ ] Buyer receives notification when seller confirms delivery.
- [ ] Buyer has 24 hours after seller confirms delivery to: confirm receipt OR raise a dispute.
- [ ] If buyer takes no action within 24 hours of seller delivery confirmation, escrow auto-releases to seller.
- [ ] Dispute requires buyer to upload at least 1 photo and a written description.
- [ ] Buyer can message seller directly from the item listing or order screen.

---

#### 5. In-App Messaging (Buyer ↔ Seller)

**User Stories:**
- As a buyer, I want to message a seller before purchasing to ask questions about an item so that I can make an informed decision.
- As a seller, I want to communicate with buyers about order logistics so that deliveries go smoothly.

**Acceptance Criteria:**
- [ ] Chat is triggered from a listing page or an active order.
- [ ] Messages are delivered in real time when both parties are online; push notification when offline.
- [ ] Chat history is persisted locally and synced to server.
- [ ] No unsolicited messaging — a chat can only be initiated from a listing or an order.

---

#### 6. Mutual Rating System

**User Stories:**
- As a buyer, I want to rate a seller after a completed transaction so that the community knows who is trustworthy.
- As a seller, I want to rate a buyer after a completed transaction so that other sellers know which buyers are reliable.

**Acceptance Criteria:**
- [ ] Both buyer and seller are prompted to rate each other after order completion or dispute resolution.
- [ ] Rating is 1–5 stars with an optional written review.
- [ ] Ratings and average score are visible on seller listings and user profiles.
- [ ] Users with average rating below 2.0 after 10+ transactions are flagged for admin review.

---

#### 7. Partner Business Payments (QR Code)

**User Stories:**
- As a student, I want to scan a QR code at a partner business to pay directly from my wallet so that I don't need cash on campus.
- As a partner business, I want to receive a daily settlement of all student payments so that my cash flow is predictable.

**Acceptance Criteria:**
- [ ] Student opens the app, taps "Scan to Pay," and scans a static QR code at the business till.
- [ ] Payment confirmation screen shows: business name, amount to be deducted, and student's available balance.
- [ ] Student confirms payment; wallet is debited instantly.
- [ ] Partner business has a separate lightweight Android app to generate/display QR codes and view incoming transactions.
- [ ] End-of-day batch settlement report is generated for each partner business.
- [ ] M-Pesa B2B or bank transfer is used to settle partner business funds daily.

---

### 🟡 Nice-to-Have Features (Post-Launch)

- **Partner Business Menu Listing:** Cafeterias and local restaurants can list their menu in the app so students can pre-order for delivery or collection.
- **Savings Goals:** Students can create named savings goals (e.g., "Semester Fees") and ring-fence a portion of their wallet balance.
- **Referral Programme:** Students earn a small wallet credit for referring friends who complete sign-up and verification.
- **In-App Notifications Centre:** A centralised log of all past push notifications for users who missed them.
- **Seller Promotional Listings:** Sellers pay a small fee to have their listing featured at the top of the marketplace feed.

---

### 🔵 Future / v2 Features

- **Multi-campus Support:** Ability to onboard additional universities, each with their own verified student pool and local business partners.
- **Installment Payments / BNPL:** Allow students to split purchases into smaller weekly payments.
- **Group Payments:** Split a bill among multiple students (e.g., shared hostel groceries).
- **Lending / Micro-loans:** Students with good transaction history can access small emergency loans.
- **Analytics Dashboard for Sellers:** Rich insights — best-selling items, peak buying hours, revenue trends.

---

## 🏗️ Technical Architecture (KMP)

### Recommended Project Structure

```
campuswallet/
├── androidApp/                  # Android-specific entry point
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   └── kotlin/com/campuswallet/android/
│   │       ├── MainActivity.kt
│   │       ├── di/              # Android-specific DI modules
│   │       └── mpesa/           # M-Pesa Android SDK integration
│
├── iosApp/                      # iOS-specific entry point
│   ├── iosApp/
│   │   ├── AppDelegate.swift
│   │   ├── ContentView.swift
│   │   └── iOSMpesa/           # M-Pesa iOS integration
│
├── shared/                      # KMP Shared Module (maximum code here)
│   ├── src/
│   │   ├── commonMain/kotlin/com/campuswallet/shared/
│   │   │   ├── data/
│   │   │   │   ├── remote/      # Ktor API clients
│   │   │   │   ├── local/       # SQLDelight database
│   │   │   │   ├── repository/  # Repository implementations
│   │   │   │   └── model/       # DTOs & data models
│   │   │   ├── domain/
│   │   │   │   ├── model/       # Domain entities
│   │   │   │   ├── repository/  # Repository interfaces
│   │   │   │   └── usecase/     # Business logic use cases
│   │   │   ├── presentation/
│   │   │   │   ├── wallet/      # Wallet ViewModels/States
│   │   │   │   ├── marketplace/ # Marketplace ViewModels/States
│   │   │   │   ├── auth/        # Auth ViewModels/States
│   │   │   │   ├── messaging/   # Chat ViewModels/States
│   │   │   │   └── payment/     # Payment ViewModels/States
│   │   │   ├── di/              # Koin shared DI modules
│   │   │   └── util/            # Extensions, formatters, validators
│   │   ├── androidMain/         # Android-specific implementations
│   │   │   └── kotlin/com/campuswallet/shared/
│   │   │       ├── db/          # SQLDelight Android driver
│   │   │       └── platform/    # Android platform implementations
│   │   └── iosMain/             # iOS-specific implementations
│   │       └── kotlin/com/campuswallet/shared/
│   │           ├── db/          # SQLDelight iOS driver
│   │           └── platform/    # iOS platform implementations
│
├── partnerApp/                  # Separate lightweight app for businesses
│   ├── androidApp/              # Android only (businesses use Android)
│   └── shared/                  # Minimal shared module for partner app
│
└── backend/                     # Optional: Ktor backend (if self-hosted)
    └── src/main/kotlin/
        ├── routes/
        ├── services/
        └── plugins/
```

---

### Layer-by-Layer Architecture

#### UI Layer (Platform Specific — Android & iOS)

- **Android:** Jetpack Compose with Material 3
- **iOS:** SwiftUI
- Both consume shared `ViewModel` classes from the shared module via `StateFlow` (Android) and `collect` wrappers (iOS via KMP-NativeCoroutines)
- Navigation handled by **Decompose** — decompose navigates via shared logic, platform renders the screen

```
UI Layer Responsibilities:
- Render state emitted by ViewModels
- Forward user events/intents to ViewModels
- Handle platform-specific UI (permissions dialogs, camera, biometrics)
- NO business logic lives here
```

#### Presentation Layer (Shared — MVI Pattern)

Use **MVI (Model-View-Intent)** architecture. This is the right call for this app because:
- Financial apps have complex state machines (escrow states, verification states, payment flows)
- MVI makes state predictable and auditable — critical for fintech
- Works cleanly with KMP shared ViewModels

```kotlin
// Example: WalletViewModel in shared module
class WalletViewModel(
    private val getWalletBalanceUseCase: GetWalletBalanceUseCase,
    private val sendMoneyUseCase: SendMoneyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WalletState())
    val state: StateFlow<WalletState> = _state.asStateFlow()

    fun onIntent(intent: WalletIntent) {
        when (intent) {
            is WalletIntent.LoadBalance -> loadBalance()
            is WalletIntent.SendMoney -> sendMoney(intent.recipient, intent.amount, intent.note)
            is WalletIntent.RequestReversal -> requestReversal(intent.transactionId)
        }
    }
}

data class WalletState(
    val balance: Double = 0.0,
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class WalletIntent {
    object LoadBalance : WalletIntent()
    data class SendMoney(val recipient: String, val amount: Double, val note: String?) : WalletIntent()
    data class RequestReversal(val transactionId: String) : WalletIntent()
}
```

#### Domain Layer (Shared)

- Pure Kotlin, zero Android/iOS dependencies
- Contains all business logic as **Use Cases**
- Repository interfaces defined here (implemented in data layer)

**Key Use Cases:**
```
auth/
  ├── RegisterStudentUseCase
  ├── VerifyOTPUseCase
  ├── LoginUseCase
  └── UploadStudentIDUseCase

wallet/
  ├── GetWalletBalanceUseCase
  ├── GetTransactionHistoryUseCase
  ├── SendMoneyUseCase
  ├── RequestReversalUseCase
  ├── SetBudgetUseCase
  └── GetBudgetProgressUseCase

marketplace/
  ├── GetListingsUseCase
  ├── SearchListingsUseCase
  ├── CreateListingUseCase
  ├── PlaceOrderUseCase
  ├── ConfirmDeliveryUseCase
  ├── RaiseDisputeUseCase
  └── RateTransactionUseCase

messaging/
  ├── GetChatHistoryUseCase
  └── SendMessageUseCase

payment/
  ├── InitiateQRPaymentUseCase
  └── TopUpViaLNMUseCase
```

#### Data Layer (Shared + Platform-Specific Drivers)

- **Remote:** Ktor HTTP client with JSON serialization (kotlinx.serialization)
- **Local:** SQLDelight for offline-first caching
- **Real-time:** Ktor WebSockets for chat and live stock updates
- **Repository pattern** bridges remote and local data sources

---

### Platform-Specific vs Shared Code Split

| Feature | Shared (KMP) | Android Specific | iOS Specific |
|---|---|---|---|
| Business logic / Use Cases | ✅ 100% | — | — |
| ViewModels / State | ✅ 100% | — | — |
| API calls (Ktor) | ✅ 100% | — | — |
| Local DB (SQLDelight) | ✅ Schema & queries | Driver only | Driver only |
| M-Pesa STK Push | ❌ | ✅ Daraja Android SDK | ✅ Daraja iOS SDK |
| Camera (ID upload, item photos) | Interface only | ✅ CameraX | ✅ UIImagePickerController |
| QR Code Scanner | Interface only | ✅ ML Kit / ZXing | ✅ AVFoundation |
| Biometric Auth | Interface only | ✅ BiometricPrompt | ✅ LocalAuthentication |
| Push Notifications | Interface only | ✅ FCM | ✅ APNs |
| Contacts Access | Interface only | ✅ ContactsProvider | ✅ Contacts Framework |
| UI (Compose / SwiftUI) | ❌ | ✅ Jetpack Compose | ✅ SwiftUI |
| Navigation (Decompose) | ✅ 100% | Render only | Render only |

**Estimated code split: ~75% shared / 25% platform-specific**

---

### Recommended Libraries

#### Core KMP
| Library | Purpose |
|---|---|
| `Ktor Client` | HTTP networking (REST + WebSockets) |
| `SQLDelight` | Type-safe local database, offline-first |
| `Koin` | Dependency injection (KMP-compatible) |
| `Decompose` | Shared navigation and lifecycle |
| `kotlinx.serialization` | JSON serialization |
| `kotlinx.coroutines` | Async/concurrency |
| `KMP-NativeCoroutines` | Expose Kotlin Flows to Swift cleanly |
| `Multiplatform Settings` | Key-value storage (tokens, preferences) |
| `Napier` | KMP logging |
| `Kotlinx DateTime` | Date/time handling across platforms |

#### Android
| Library | Purpose |
|---|---|
| `Jetpack Compose` | UI |
| `Material 3` | Design system |
| `CameraX` | Camera for ID upload & item photos |
| `ML Kit Barcode Scanning` | QR code scanning |
| `Firebase Cloud Messaging` | Push notifications |
| `Coil` | Image loading |
| `BiometricPrompt` | Fingerprint/face auth |

#### iOS
| Library | Purpose |
|---|---|
| `SwiftUI` | UI |
| `AVFoundation` | QR code scanning |
| `APNs` | Push notifications |
| `Kingfisher` or `SDWebImageSwiftUI` | Image loading |
| `LocalAuthentication` | Face ID / Touch ID |

#### Backend (Recommended: Ktor Server on Railway or Render)
| Library | Purpose |
|---|---|
| `Ktor Server` | REST API + WebSocket server |
| `Exposed` or `ktorm` | ORM for PostgreSQL |
| `PostgreSQL` | Primary database |
| `Redis` | Session management, escrow state cache |
| `Firebase Admin SDK` | Push notifications |
| `Daraja API (Safaricom)` | M-Pesa STK Push & C2B |

---

### Architectural Patterns

**Primary Pattern: MVI (Model-View-Intent)**  
Reason: Fintech apps demand single source of truth, predictable state transitions, and auditability of actions. MVI enforces this rigorously.

**Secondary Pattern: Repository + Use Case (Clean Architecture)**  
Reason: Separates data concerns from business logic. Critical for testability — you can unit test every use case without a device or network.

**Real-time Pattern: WebSocket + Optimistic UI**  
For chat and stock updates, maintain a persistent WebSocket connection. Apply optimistic UI updates locally, then confirm/rollback from server response.

**Escrow State Machine:**
```
ORDER STATES:
PENDING → PAID (escrow held) → DELIVERED (seller confirms) → COMPLETED (buyer confirms / auto-release)
                                                           ↘ DISPUTED → RESOLVED_REFUND / RESOLVED_RELEASE
```

---

### Known Technical Risks & Mitigations

| Risk | Severity | Mitigation |
|---|---|---|
| M-Pesa webhook reliability | High | Implement idempotent webhook handlers with retry queue; reconcile balances daily |
| Escrow race conditions | High | Use database transactions with row-level locking; never update escrow state outside a transaction |
| KMP iOS Coroutines complexity | Medium | Use KMP-NativeCoroutines library; wrap Flows with `@NativeCoroutines` annotation |
| Real-time WebSocket on mobile (battery/connectivity) | Medium | Implement exponential backoff reconnection; fall back to polling on weak networks |
| Student ID verification bottleneck | Medium | Build admin review queue early; consider semi-automated OCR check as pre-filter |
| Image storage costs at scale | Low-Medium | Use Cloudflare R2 or AWS S3 with lifecycle policies; compress images on upload before storing |
| Solo developer burnout | High | Prioritise Phase 1 core wallet features first; don't build marketplace until wallet is stable |

---

## 🔐 Security & Compliance

### Security Architecture

**Authentication:**
- Phone number + password with bcrypt hashing (min 12 rounds)
- SMS OTP via Africa's Talking or Twilio for phone verification
- JWT access tokens (15-minute expiry) + refresh token rotation
- Biometric auth as a convenience layer over token management

**Transaction Security:**
- All wallet mutations require re-authentication for amounts above KES 1,000
- Every financial transaction generates an immutable audit log entry
- Escrow state changes require server-side validation — never trust client
- Rate limiting on all payment endpoints (max 10 transactions per minute per user)

**Data Protection (Kenya Data Protection Act 2019 compliance):**
- Collect only data necessary for the stated purpose (data minimisation)
- Student ID photos encrypted at rest (AES-256)
- User data deletion endpoint available in account settings
- Clear, explicit consent during onboarding for data usage
- Audit logs retained for 7 years (financial regulation requirement)
- Data stored on servers within Kenya where possible

**Network Security:**
- TLS 1.3 for all API communication
- Certificate pinning on mobile clients
- API keys and secrets never hardcoded — loaded from environment variables
- M-Pesa webhook endpoint validated via Safaricom IP whitelist

**Fraud Prevention:**
- Device fingerprinting on login — alert user on new device sign-in
- Anomaly detection: flag accounts with unusual transfer velocity
- Cooling-off period: new accounts limited to KES 5,000 daily transfer limit for first 30 days
- Seller accounts flagged after 3 unresolved disputes

---

## 📊 Admin & Operations

### Admin Panel (Web Dashboard)

The following admin capabilities are required to operate the platform:

**User Management:**
- View all registered students with verification status
- Approve or reject student ID submissions with reason
- Suspend or ban accounts (with audit trail)
- View full transaction history per user

**Marketplace Moderation:**
- View all active listings; remove listings that violate policy
- Manage dispute queue: review evidence photos, make final decisions on refunds
- View seller performance metrics and ratings

**Financial Operations:**
- View total funds held in escrow at any given time
- Trigger manual escrow release or refund in exceptional cases
- Generate daily/weekly/monthly transaction reports
- Manage partner business settlement — view pending settlements, trigger manual payouts

**Partner Business Management:**
- Onboard new partner businesses and generate their QR codes
- View transaction volume per partner business
- Configure daily settlement schedule and payout method

**Platform Analytics:**
- Daily/monthly active users
- Transaction volume and value over time
- Marketplace GMV (gross merchandise value)
- Top selling categories and items
- User acquisition and retention metrics

> **Recommendation:** Build the admin panel as a web app using Ktor + React or Next.js. Do not build it into the mobile app — keep operational tooling separate.

---

## 🔔 Notifications Strategy

### Push Notification Events

| Event | Recipient | Message |
|---|---|---|
| Money received (transfer) | Receiver | "You received KES {amount} from {sender}" |
| Money sent confirmation | Sender | "KES {amount} sent to {recipient}" |
| Reversal requested | Original receiver | "{sender} has requested a reversal of KES {amount}" |
| Reversal approved/rejected | Requester | "Your reversal request was approved/declined" |
| Low balance alert | User | "Your balance is below KES {threshold}" |
| New order placed | Seller | "New order for {item} — confirm to proceed" |
| Seller confirmed delivery | Buyer | "{seller} confirmed delivery. Please confirm receipt within 24 hours" |
| Buyer confirmed receipt | Seller | "Order complete! KES {amount} released to your wallet" |
| Dispute raised | Seller | "A dispute has been raised for order #{id}. Check your orders." |
| Dispute resolved | Both | "Dispute #{id} resolved: {outcome}" |
| Student ID verified | Student | "Your student ID has been verified. You can now sell on CampusWallet!" |
| New chat message | Recipient | "{sender}: {message preview}" |
| QR payment successful | Student | "KES {amount} paid to {business name}" |
| Budget 80% reached | User | "You've used 80% of your {category} budget this month" |

### Notification Delivery
- **FCM** (Firebase Cloud Messaging) for Android
- **APNs** for iOS
- In-app notification centre for users who miss push notifications
- All notifications routed through shared Kotlin logic, platform delivers them

---

## 🚀 Phased Delivery Roadmap

Given a solo developer, the following build sequence is strongly recommended. Build each phase to production quality before moving to the next.

### Phase 1 — Foundation (Wallet Core)
*Goal: Students can register, top up, and send money to each other*
- KMP project setup and architecture scaffolding
- Backend API (Ktor) + PostgreSQL schema
- Student registration, OTP verification, login
- Student ID upload + admin verification flow
- Wallet balance display and transaction history
- M-Pesa Daraja integration (STK Push + C2B webhook)
- Peer-to-peer money transfers (contacts sync + manual entry)
- Transfer reversal requests
- Push notifications for wallet events
- Budget setting and low-balance alerts

### Phase 2 — Marketplace
*Goal: Verified students can list and buy items with escrow protection*
- Item listing with photos, categories, stock management
- Marketplace feed (trending), category filter, search
- Escrow payment flow (buy → hold → deliver → release/dispute)
- Dispute system with photo evidence upload
- Mutual rating system
- Seller dashboard (inventory + earnings)
- In-app messaging (buyer ↔ seller)

### Phase 3 — Partner Businesses
*Goal: Students can pay at campus businesses via QR code*
- Partner business onboarding flow
- QR code generation for partner businesses
- Partner business lightweight app (QR display + transaction log)
- QR payment flow from student app
- Daily batch settlement reporting and payout

### Phase 4 — Polish & Scale
*Goal: Platform is ready for multi-campus expansion*
- Admin web dashboard (full feature set)
- Performance optimisation and caching
- Multi-campus architecture (tenant isolation)
- App Store and Play Store submission
- Analytics and business intelligence dashboards
- Monetisation: seller listing fee implementation

---

## 💰 Monetisation Model

| Revenue Stream | Description | When to Activate |
|---|---|---|
| Seller Listing Fee | Monthly or per-listing fee for student entrepreneurs to maintain an active shop on the marketplace | Phase 4 (after critical mass) |
| Partner Business Commission | Small percentage (0.5–1%) on QR transactions processed through the platform | Phase 3 |
| Featured Listings | Sellers pay a one-time fee to promote a listing to the top of the feed | Phase 4 |
| Transaction Fee (future) | Micro-fee on peer-to-peer transfers above a certain threshold | v2 |

> **Note:** Do not charge any fees in Phase 1 or 2. Build trust and reach critical mass first. Introduce fees only when users are locked in and value is proven.

---

*Document generated from discovery interview. Last updated: March 2026.*
