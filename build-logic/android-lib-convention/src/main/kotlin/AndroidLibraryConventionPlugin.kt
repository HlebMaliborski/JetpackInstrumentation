@file:Suppress("unused")

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import plugins.configureKotlinAndroid
import plugins.getLib
import plugins.getPluginId

/**
 * Convention plugin for agent modules that configures the basic settings.
 *
 * @author gleb.maliborsky
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val agentLibs = project.extensions.getByType<VersionCatalogsExtension>().named("agentLibs")
        val testLibs = project.extensions.getByType<VersionCatalogsExtension>().named("testLibs")

        with(project) {
            with(pluginManager) {
                apply(agentLibs.getPluginId("android.lib"))
                apply(agentLibs.getPluginId("kotlin.android"))
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                dependencies {
                    add("testImplementation", testLibs.getLib("mockk"))
                }
            }
        }
    }
}