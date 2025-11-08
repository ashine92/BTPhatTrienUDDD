// D:/Study/DH/PhatTrienApp/BTChuong5RoomDatabase/settings.gradle.kts

pluginManagement {
    repositories {
        google()
        mavenCentral()
        // This is the crucial part that was missing or not being recognized.
        // It tells Gradle to look for plugins on the Gradle Plugin Portal.
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BTChuong5RoomDatabase"
include(":app")