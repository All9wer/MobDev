pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
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

rootProject.name = "chirkov.Lesson2"
include(":app")
include(":multiactivity")
include(":intentfilter")
include ':vcs-1'
include(":dialogspractice")
include ':lab2:app'
include ':lab2:multiactivity'
include ':lab2:vcs-1'
