import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlytics)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.zenithapps.mobilestack.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.zenithapps.mobilestack.android" // TODO: Your app id here
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.1"
    }
    signingConfigs {
        val prop = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "local.properties")))
        }
        getByName("debug") {
            storeFile = file("debug_keystore.jks")
            storePassword = prop.getProperty("DEBUG_PASSWORD")
            keyAlias = "androiddebugkey"
            keyPassword = prop.getProperty("DEBUG_PASSWORD")
        }
        create("release") {
            storeFile = file("release_keystore.jks")
            storePassword = prop.getProperty("RELEASE_PASSWORD")
            keyAlias = "androidreleasekey"
            keyPassword = prop.getProperty("RELEASE_PASSWORD")
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders["crashlyticsEnabled"] = "false"
        }
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["crashlyticsEnabled"] = "true"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.activity.compose)
}