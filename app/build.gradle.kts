plugins {
    alias(libs.plugins.hilt)

    id("com.android.application")
    kotlin("android")
    kotlin("plugin.parcelize")
    kotlin("kapt")

    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")

    alias(libs.plugins.grgit)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "org.wycliffe.mypd"

    baseConfiguration(project)
    configureCompose(project)
    defaultConfig {
        applicationId = "org.wycliffe.mypd"
        versionName = project.version.toString()
        versionCode = grgit.log(mapOf("includes" to listOf("HEAD"))).size

        buildConfigField("String", "AUTH_PROVIDER", "\"API_OAUTH\"")

        proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
        proguardFile("proguard-rules.pro")
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    buildTypes {
        named("debug") {
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".dev"
        }
        register("qa") {
            initWith(getByName("debug"))
            matchingFallbacks += listOf("debug")
            versionNameSuffix = "-qa"
            applicationIdSuffix = ".qa"

            if (project.hasProperty("firebaseAppDistributionBuild")) {
                signingConfig = signingConfigs.create("firebaseAppDistribution") {
                    storeFile = project.properties["firebaseAppDistributionKeystorePath"]?.let { rootProject.file(it) }
                    storePassword = project.properties["firebaseAppDistributionKeystoreStorePassword"].toString()
                    keyAlias = project.properties["firebaseAppDistributionKeystoreKeyAlias"].toString()
                    keyPassword = project.properties["firebaseAppDistributionKeystoreKeyPassword"].toString()
                }

                firebaseAppDistribution {
                    appId = "1:1053463711496:android:c195e3579737ad79bca6bd"
                    releaseNotes = generateFirebaseAppDistributionReleaseNotes()
                    serviceCredentialsFile = rootProject.file("firebase/firebase_api_key.json").toString()
                    groups = "android-testers"
                }
            }
            isMinifyEnabled = true
            isShrinkResources = true
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
    sourceSets {
        named("qa") {
            java.srcDir("src/debug/java")
            kotlin.srcDir("src/debug/kotlin")
            res.srcDir("src/debug/res/values")
            manifest.srcFile("src/debug/AndroidManifest.xml")
        }
    }
    configurations {
        named("qaImplementation") { extendsFrom(getByName("debugImplementation")) }
    }
    kapt {
        javacOptions {
            // Increase the max count of errors from annotation processors.
            // Default is 100.
            option("-Xmaxerrs", 9999)
        }
    }

    flavorDimensions.add("env")
    productFlavors {
        register("production") {
            manifestPlaceholders += mapOf("appAuthRedirectScheme" to "org.wycliffe.mypd")

            buildConfigField("String", "MPDX_API_BASE_URI", "\"https://api.mypd.wycliffe.org/\"")

            buildConfigField("String", "AUTH_END_POINT", "\"https://api.mypd.wycliffe.org/oauth/authorize\"")
            buildConfigField("String", "TOKEN_END_POINT", "\"https://api.mypd.wycliffe.org/oauth/token\"")
            buildConfigField("String", "CLIENT_ID", "\"aW-Zw4i-53xUrUfycD5oVNqscTbV5kfMJGuJaafSAh4\"")
            buildConfigField("String", "REDIRECT_URI", "\"org.wycliffe.mypd:/oauth\"")

            manifestPlaceholders += mapOf("hostMpdxWeb" to "mypd.wycliffe.org")
        }

        register("development") {
            manifestPlaceholders += mapOf("appAuthRedirectScheme" to "org.wycliffe.mypd-test")

            buildConfigField("String", "MPDX_API_BASE_URI", "\"https://api.mypd-test.wycliffe.org/\"")

            buildConfigField("String", "AUTH_END_POINT", "\"https://api.mypd-test.wycliffe.org/oauth/authorize\"")
            buildConfigField("String", "TOKEN_END_POINT", "\"https://api.mypd-test.wycliffe.org/oauth/token\"")
            buildConfigField("String", "CLIENT_ID", "\"ssIevsY4bXWq6y_DitfNMiP2y5FIInkspcQ7LQFA2WU\"")
            buildConfigField("String", "REDIRECT_URI", "\"org.wycliffe.mypd-test:/oauth\"")

            manifestPlaceholders += mapOf("hostMpdxWeb" to "mypd-test.wycliffe.org")
        }
    }
}

dependencies {
    implementation(libs.mpdx.lib.lib)
    implementation(libs.mpdx.lib.core)
    implementation(libs.androidx.core.ktx)

    implementation(libs.mpdx.lib.oauth)

    implementation(libs.openid.appauth)

    implementation(libs.firebase.core)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.iid)

    implementation(libs.gtoSupport.api.okhttp3)
    implementation(libs.gtoSupport.base)
    implementation(libs.gtoSupport.compat)
    implementation(libs.gtoSupport.core)
    implementation(libs.gtoSupport.dagger)
    implementation(libs.gtoSupport.firebase.crashlytics)
    implementation(libs.gtoSupport.facebook.flipper)
    implementation(libs.gtoSupport.jsonapi.retrofit2)
    implementation(libs.gtoSupport.kotlin.coroutines)
    implementation(libs.gtoSupport.okhttp3)
    implementation(libs.gtoSupport.util)
    implementation(libs.gtoSupport.okta)

    implementation(libs.weakDelegate)
    implementation(libs.eventbus)
    implementation(libs.gson)
    implementation(libs.guava)
    implementation(libs.hilt)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.coroutines.playServices)
    implementation(libs.materialComponents)
    implementation(libs.threetenbp.android)
    implementation(libs.timber)

    // debugging tools
    debugImplementation(libs.facebook.flipper)
    debugImplementation(libs.facebook.flipper.plugins.network)
    debugImplementation(libs.facebook.flipper.plugins.realm)
    debugImplementation(libs.facebook.soloader)
    debugImplementation(libs.leakcanary)
    debugImplementation(libs.okhttp3.interceptors.logging)
    debugImplementation(libs.gtoSupport.leakcanary)

    kapt(libs.dagger.compiler)
    kapt(libs.hilt.compiler)
}

fun generateFirebaseAppDistributionReleaseNotes(size: Int = 10): String {
    var output = "Recent changes:\n\n"
    grgit.log {
        maxCommits = size
    }.forEach { commit ->
        output = output + "* " + commit.shortMessage + "\n"
    }
    return output
}
