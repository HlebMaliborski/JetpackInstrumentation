package plugins

import org.gradle.api.artifacts.VersionCatalog

/**
 * Finds library in version catalog.
 */
internal fun VersionCatalog.getLib(name: String): Any {
    return this.findLibrary(name).get()
}

/**
 * Finds plugins id in version catalog.
 */
internal fun VersionCatalog.getPluginId(name: String): String {
    return this.findPlugin(name).get().get().pluginId
}

/**
 * Finds version in version catalog.
 */
internal fun VersionCatalog.getVersion(name: String): String {
    return this.findVersion(name).get().displayName
}