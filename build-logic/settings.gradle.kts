rootProject.name = "build-logic"
val repositoryBaseURL: String by settings

dependencyResolutionManagement {
    repositories {
        maven { url = uri("$repositoryBaseURL/google/") }
        maven { url = uri("$repositoryBaseURL/repo1/") }
    }
    versionCatalogs {
        create("agentLibs") {
            from(files("../gradle/agent.libs.versions.toml"))
        }
        create("testLibs") {
            from(files("../gradle/test.libs.versions.toml"))
        }
    }
}

include(":android-lib-convention")