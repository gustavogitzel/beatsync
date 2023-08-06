rootProject.name = "beatsync"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
)

include(
    "beatsync-deployments:beatsync-search"
)

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

