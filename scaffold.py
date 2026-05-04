import os

base_dir = r"d:\Android\Projects\Ndugu\feature"

features_to_scaffold = ["marketplace", "messaging", "payment"]

domain_gradle = """import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.ndugu.feature.FEATURE_NAME.domain"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
"""

data_gradle = """import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.database)
            implementation(projects.feature.FEATURE_NAME.domain)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.clientCore)
            implementation(libs.ktor.clientContentNegotiation)
            implementation(libs.ktor.serializationKotlinxJson)
            implementation(libs.koin.core)
            implementation(libs.kermit)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.ndugu.feature.FEATURE_NAME.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
"""

presentation_gradle = """import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.presentation)
            implementation(projects.core.designSystem)
            implementation(projects.feature.FEATURE_NAME.domain)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.material.icons.extended)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
        }
    }
}

android {
    namespace = "com.example.ndugu.feature.FEATURE_NAME.presentation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}
"""

usecase_template = """package com.example.ndugu.feature.FEATURE_NAME.domain.usecase

class USECASE_NAME {
    // TODO: Implement USECASE_NAME
}
"""

usecases = {
    "auth": ["RegisterStudentUseCase", "VerifyOTPUseCase", "LoginUseCase", "UploadStudentIDUseCase"],
    "wallet": ["GetWalletBalanceUseCase", "GetTransactionHistoryUseCase", "SetBudgetUseCase", "GetBudgetProgressUseCase"],
    "transfer": ["SendMoneyUseCase", "RequestReversalUseCase"],
    "marketplace": ["GetListingsUseCase", "SearchListingsUseCase", "CreateListingUseCase", "PlaceOrderUseCase", "ConfirmDeliveryUseCase", "RaiseDisputeUseCase", "RateTransactionUseCase"],
    "messaging": ["GetChatHistoryUseCase", "SendMessageUseCase"],
    "payment": ["InitiateQRPaymentUseCase", "TopUpViaLNMUseCase"]
}

# 1. Scaffold new feature modules
for feature in features_to_scaffold:
    feat_dir = os.path.join(base_dir, feature)
    
    # Create subdirs and gradles
    for layer, gradle_content in [("domain", domain_gradle), ("data", data_gradle), ("presentation", presentation_gradle)]:
        layer_dir = os.path.join(feat_dir, layer)
        os.makedirs(layer_dir, exist_ok=True)
        gradle_path = os.path.join(layer_dir, "build.gradle.kts")
        if not os.path.exists(gradle_path):
            with open(gradle_path, 'w', encoding='utf-8') as f:
                f.write(gradle_content.replace("FEATURE_NAME", feature))

# 2. Add use cases for all
for feature, cases in usecases.items():
    uc_dir = os.path.join(base_dir, feature, "domain", "src", "commonMain", "kotlin", "com", "example", "ndugu", "feature", feature, "domain", "usecase")
    os.makedirs(uc_dir, exist_ok=True)
    
    for case in cases:
        uc_file = os.path.join(uc_dir, f"{case}.kt")
        if not os.path.exists(uc_file):
            with open(uc_file, 'w', encoding='utf-8') as f:
                content = usecase_template.replace("FEATURE_NAME", feature).replace("USECASE_NAME", case)
                f.write(content)

print("Scaffolding complete.")
