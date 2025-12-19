plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ru.mirea.chirkovia.yandexmaps"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.mirea.chirkovia.yandexmaps"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        val mapkitApiKey = rootProject.file("local.properties")
            .takeIf { it.exists() }
            ?.readLines()
            ?.firstOrNull { it.startsWith("MAPKIT_API_KEY=") }
            ?.substringAfter("=")
            ?.trim()
            ?: ""
        buildConfigField("String", "MAPKIT_API_KEY", "\"$mapkitApiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("com.yandex.android:maps.mobile:4.3.1-full")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}