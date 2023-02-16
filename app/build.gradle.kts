import Dependencies.compose

plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    id("com.example.jetpackinstrumentation")
}

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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
gradle.taskGraph.whenReady {  }
configure<InstrumentationPluginExtension> {
    val list = ArrayList<String>()
    list.add("androidx.compose.foundation.ClickableKt")
    list.add("androidx.compose.material.CheckboxKt")
    instrumentedClasses.set(list)
}

dependencies {
    implementation(Dependencies.AndroidX.APPCOMPAT)
    implementation(Dependencies.AndroidX.LIFECYCLE)
    implementation(Dependencies.Google.MATERIAL)
    implementation("androidx.compose.foundation:foundation:1.4.0-alpha04")

    compose()
    androidTestImplementation(Dependencies.Test.Integration.COMPOSE_UI)
    debugImplementation(Dependencies.Test.Integration.COMPOSE_TOOLING)
}