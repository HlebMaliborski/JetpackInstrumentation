plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // This should be in-sync with the Kotlin version exposed by `Versions.kt`
    implementation("com.android.tools.build:gradle:7.3.0")

    // This should be in-sync with the Kotlin version exposed by `Versions.kt`
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
    implementation("org.ow2.asm:asm-util:9.5")
    gradleApi()
}
gradlePlugin {
    plugins {
        register("instrumentation") {
            description = "plugin"
            displayName = "plugin"
            id = "com.example.jetpackinstrumentation"
            implementationClass = "InstrumentationPlugin"
        }
    }
}
