rootProject.name = "Ndugu"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

// App modules
include(":composeApp")
include(":server")
include(":shared")

// Core modules
include(":core:domain")
include(":core:data")
include(":core:presentation")
include(":core:design-system")
include(":core:database")

// Feature: Auth
include(":feature:auth:domain")
include(":feature:auth:data")
include(":feature:auth:presentation")

// Feature: Wallet
include(":feature:wallet:domain")
include(":feature:wallet:data")
include(":feature:wallet:presentation")

// Feature: Transfer
include(":feature:transfer:domain")
include(":feature:transfer:data")
include(":feature:transfer:presentation")

// Feature: Marketplace
include(":feature:marketplace:domain")
include(":feature:marketplace:data")
include(":feature:marketplace:presentation")

// Feature: Messaging
include(":feature:messaging:domain")
include(":feature:messaging:data")
include(":feature:messaging:presentation")

// Feature: Payment
include(":feature:payment:domain")
include(":feature:payment:data")
include(":feature:payment:presentation")
