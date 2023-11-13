import Dependencies.compose

plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    id("com.example.jetpackinstrumentation")
}

apply<plugin.GenerateClassPlugin>()

android {
    namespace = "com.example.jetpackinstrumentation"
    compileSdk = Versions.App.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.App.MIN_SDK
        targetSdk = Versions.App.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

configure<InstrumentationPluginExtension> {
    val list = ArrayList<String>()
    list.add("androidx.compose.foundation.ClickableKt")
    instrumentedClasses.set(list)
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.compose.foundation:foundation:1.4.0")
    // implementation(project(":mylibrary"))
    compose()
    androidTestImplementation(Dependencies.Test.Integration.COMPOSE_UI)
    debugImplementation(Dependencies.Test.Integration.COMPOSE_TOOLING)
}
