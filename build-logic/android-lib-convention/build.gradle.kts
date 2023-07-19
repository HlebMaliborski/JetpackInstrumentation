plugins {
    `kotlin-dsl`
}

group = "com.dynatrace.build-logic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(agentLibs.plugin.agp)
    compileOnly(agentLibs.plugin.kotlin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "com.dynatrace.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}