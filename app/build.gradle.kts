import GradlePlugins.android
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id(GradlePlugins.android)
    kotlin(GradlePlugins.kotlinAndroid)
    kotlin(GradlePlugins.kotlinApt)
    kotlin(GradlePlugins.kotlinExt)
    id(GradlePlugins.jaredsburrows)
}

apply {
    plugin(GradlePlugins.navigationSafeKotlin)
    plugin(GradlePlugins.fabric)
    plugin(GradlePlugins.playService)
//    from("../ktlint.gradle")
    from("../git-hook.gradle")
    from("../publish.gradle")
}

android {
    compileSdkVersion(Android.targetSdk)

    defaultConfig {
        applicationId = Android.applicationId
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = AndroidJUnit.runner
    }

    buildTypes {
        getByName(BuildType.release) {
            isMinifyEnabled = BuildType.minifyRelease
            isShrinkResources = BuildType.isShrinkResourcesRelease

            proguardFiles(BuildType.proguardRelease)
        }

        getByName(BuildType.debug) {
            isTestCoverageEnabled = true
            isMinifyEnabled = BuildType.minifyDebug
            isShrinkResources = BuildType.isShrinkResourcesDebug
            signingConfig = signingConfigs[BuildType.debug]
        }

//        create(BuildType.staging) {
//            isMinifyEnabled = BuildType.minifyStaging
//            isShrinkResources = BuildType.isShrinkResourcesStaging
//
//            proguardFiles(BuildType.proguardStaging)
//            signingConfig = signingConfigs[BuildType.debug]
//        }
    }

    flavorDimensions("tier")

    productFlavors {
        create("develop") {
            applicationIdSuffix = ".dev"
            matchingFallbacks = listOf("debug")
        }

        create("production") {
            matchingFallbacks = listOf("release")
        }

        create("mock") {
            applicationIdSuffix = ".mock"
            matchingFallbacks = listOf("debug")
        }
    }

    applicationVariants.all {
        var isMock = false
        val baseUrl = "https://api.themoviedb.org/3/"
        val apiKey= "f64c520a006b21aa8ea0f224091f1bfc"

        when {
            name.contains("develop") -> {

            }
            name.contains("production") -> {

            }
            name.contains("mock") -> {
                isMock = true
            }
        }

        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("Boolean", "IS_MOCK", "$isMock")

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        val options = this as KotlinJvmOptions
        options.jvmTarget = "1.8"
    }

    dataBinding {
        isEnabled = true
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    // ktx core
    implementation(Libs.ktx)
    implementation(Libs.stdLib)

    // support
    implementation(Libs.supportAppCompat)
    implementation(Libs.supportAnnotations)
    implementation(Libs.supportDesign)
    implementation(Libs.supportCardview)
    implementation(Libs.supportRecyclerview)
    implementation(Libs.supportRecyclerviewSelection)
    implementation(Libs.supportLegacyV4)

    // lifecycle
    implementation(Libs.lifecycleRuntime)
    implementation(Libs.lifecycleExtensions)
    implementation(Libs.lifecycleLiveDataKtx)
    implementation(Libs.lifecycleSavedState)
    implementation(Libs.fragmentKtx)

    // Constraint Layout
    implementation(Libs.constraintlayout)

    // Glide
    implementation(Libs.glideRuntime)
    kapt(Libs.glideCompiler)

    // Rx
    implementation(Libs.rxJava)
    implementation(Libs.rxAndroid)

    // Binding
    kapt(Libs.lifecycleJava8)
    kapt(Libs.daggerProcessor)
    kapt(Libs.daggerCompiler)

    // Dagger 2
    implementation(Libs.daggerCore)
    implementation(Libs.daggerAndroid)
    implementation(Libs.daggerSupport)

    // Permission
    implementation(Libs.permission)

    // Navigation
    implementation(Libs.navigationRuntimeKtx)
    implementation(Libs.navigationUiKtx)
    implementation(Libs.navigationFragmentKtx)

    // Dependencies for local unit tests
    testImplementation(Libs.junit)
    testImplementation(Libs.mockitoAll)
    testImplementation(Libs.hamcrest)
    testImplementation(Libs.archTesting)
    testImplementation(Libs.stdLib)
    testImplementation(Libs.kotlinTest)
    testImplementation(Libs.mockitoWebServer)
    testImplementation(Libs.robolectric)

    // Firebase analytics & fabric
    implementation(Libs.firebaseAnalytics)
    implementation(Libs.crashAnalytics)
    implementation(Libs.firebaseAuth)
    implementation(Libs.firebaseMessaging)

    implementation(Libs.viewPager2)

    // Retrofit
    implementation(Libs.retrofitRuntime)
    implementation(Libs.retrofitGson)
    implementation(Libs.retrofitAdapter)
    implementation(Libs.retrofitScalars)

    implementation(Libs.shimmer)

    //room
    implementation(Libs.roomRxjava2)
    implementation(Libs.roomRuntime)
    implementation(Libs.roomKtx)
    kapt(Libs.roomCompiler)

    // Rx
    implementation(Libs.rxJava)
    implementation(Libs.rxAndroid)

    //log
    implementation(Libs.okLogging)
    implementation(Libs.timber)

    //coroutine
    implementation(Libs.coroutinesAdapter)
    implementation(Libs.coroutinesAndroid)
    implementation(Libs.coroutinesCore)
    testImplementation(Libs.coroutinesTest)

    //koin
    implementation(Libs.koinCore)
    implementation(Libs.koinJava)
    implementation(Libs.koinScope)
    testImplementation(Libs.koinTest)
    implementation(Libs.koinViewModel)

}
