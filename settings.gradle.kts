pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        maven {
            url = uri("https://jitpack.io")
            content {
                includeGroupByRegex("com\\.github\\..*")
            }
        }
        maven {
            url = uri("https://cruglobal.jfrog.io/artifactory/maven-mobile/")
            content {
                includeGroup("org.ccci.gto.android")
                includeGroup("org.ccci.gto.android.testing")
            }
        }
        maven {
            // This repository contains pre-release versions of the Compose Compiler
            url = uri("https://androidx.dev/storage/compose-compiler/repository/")
            content {
                includeGroup("androidx.compose.compiler")
            }
        }
        jcenter {
            content {
                includeModule("com.ochim", "recyclertablayout-androidx")
                includeModule("dk.ilios", "realmfieldnameshelper")
            }
        }
        google()
        mavenCentral()
    }
}

include(":app")
