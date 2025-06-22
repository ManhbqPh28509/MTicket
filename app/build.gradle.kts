plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "movie.fpoly.mticket"
    compileSdk = 34

    defaultConfig {
        applicationId = "movie.fpoly.mticket"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.material.v190)
    implementation(libs.viewpager2)
    implementation (libs.androidx.recyclerview)
    implementation (libs.androidx.recyclerview.selection)
    implementation(libs.androidx.cardview)
    implementation (libs.circleindicator)
    implementation (libs.imageslideshow)
    implementation(libs.androidx.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}