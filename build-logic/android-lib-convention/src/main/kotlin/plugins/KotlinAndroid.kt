package plugins

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Base configuration for Kotlin with Android options
 *
 * @author gleb.maliborsky
 */
@Suppress("UnstableApiUsage")
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("agentLibs")

    commonExtension.apply {
        compileSdk = libs.getVersion("sdk.compile").toInt()

        defaultConfig {
            minSdk = libs.getVersion("sdk.min").toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinOptions {
            jvmTarget = libs.getVersion("kotlin.jvmTarget")

            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()
        }
        testOptions {
            unitTests {
                all { tests ->
                    tests.useJUnitPlatform()
                    tests.systemProperty("java.io.tmpdir", "${buildDir}/tmp")
                }
                isIncludeAndroidResources = true
            }
        }
        buildFeatures {
            buildConfig = false
        }
    }
}

internal fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
