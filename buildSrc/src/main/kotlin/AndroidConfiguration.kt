import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun BaseAppModuleExtension.baseConfiguration(project: Project) {
    configureAndroidCommon(project)
}

fun LibraryExtension.baseConfiguration(project: Project) {
    configureAndroidCommon(project)

//    project.configurePublishing()
}

private fun TestedExtension.configureAndroidCommon(project: Project) {
    configureSdk()
    configureCompilerOptions()
    configureTestOptions(project)

    defaultConfig.vectorDrawables.useSupportLibrary = true
    lintOptions.lintConfig = project.rootProject.file("analysis/lint/lint.xml")
}

private fun BaseExtension.configureSdk() {
    compileSdkVersion(34)

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}

private fun BaseExtension.configureCompilerOptions() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    (this as ExtensionAware).extensions.findByType<KotlinJvmOptions>()?.apply {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs += "-Xjvm-default=all"
    }
}

fun CommonExtension<*, *, *, *, *>.configureCompose(project: Project) {
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion =
        project.libs.findVersion("androidx-compose-compiler").get().requiredVersion

    // add our base compose dependencies
    project.dependencies.apply {
        addProvider("implementation", project.libs.findBundle("androidx-compose").get())
    }
}

private fun TestedExtension.configureTestOptions(project: Project) {
    testOptions.unitTests {
        isIncludeAndroidResources = true

        all {
            // default is 512MB, robolectric consumes a lot of memory
            // by loading an AOSP image for each version being tested
            it.maxHeapSize = "2g"
        }
    }
    project.configurations.all {
        resolutionStrategy {
            dependencySubstitution {
                val hamcrest = module("org.hamcrest:hamcrest:${project.libs.findVersion("hamcrest").get()}")
                substitute(module("org.hamcrest:hamcrest-core")).using(hamcrest)
                substitute(module("org.hamcrest:hamcrest-library")).using(hamcrest)
            }
        }
    }

    // Kotlin Kover
    project.apply(plugin = "org.jetbrains.kotlinx.kover")
    project.extensions.configure<KoverReportExtension> {
        project.androidComponents.onVariants {
            androidReports(it.name){
                xml {
                    setReportFile(project.layout.buildDirectory.file("reports/kover/${it.name}/report.xml"))
                }
            }
        }
    }
}
