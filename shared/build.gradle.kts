plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            export(libs.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            api(libs.decompose)
            api(libs.decompose.composeExtensions)
            implementation(libs.gitlive.firebase.common)
            implementation(libs.gitlive.firebase.firestore)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.essenty.lifecycle)
            api(libs.napier)
        }
        androidMain.dependencies {
            implementation(libs.decompose.composeExtensions)
            api(project.dependencies.platform(libs.firebase.bom))
            api(libs.firebase.common)
            api(libs.firebase.analytics)
            api(libs.firebase.crashlytics)
        }
        iosMain.dependencies {
            api(libs.decompose)
            api(libs.essenty.lifecycle)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    task("testClasses") // Fix for cannot locate tasks that match ':shared:testClasses'
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.zenithapps.mobilestack.resources"
    generateResClass = always
}

android {
    namespace = "com.zenithapps.mobilestack"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
