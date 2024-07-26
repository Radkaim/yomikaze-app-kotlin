plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")

}


android {
    namespace = "com.example.yomikaze_app_kotlin"
    compileSdk = 34

    defaultConfig {

        defaultConfig {

        }
        applicationId = "org.yomikaze.app"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.wear.compose:compose-material:1.3.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.media3:media3-common:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material3:material3:1.1.0")

    //for navigation
    implementation("androidx.compose.ui:ui:1.7.0-beta02")
    implementation("androidx.compose.material:material:1.7.0-beta02")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //for auto image slider
    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")

    // paper use for shared preferences
    implementation("io.github.pilgr:paperdb:2.7.2")

    // Retrofit for network requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //hilt for dependency injection
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Coroutines for asynchronous programming
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //auto image slider
    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")
    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation("com.google.accompanist:accompanist-coil:0.15.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    //for bottom navigation animation
    // implementation("com.exyte:animated-navigation-bar:1.0.0")
    // implementation("androidx.graphics:graphics-shapes:1.0.0-alpha05")
    // for lotie animation
    implementation("com.airbnb.android:lottie-compose:4.0.0")

    //Google Play Services
    //implementation("com.google.android.gms:play-services-wallet:19.4.0")

    //for google sign in
    implementation("androidx.credentials:credentials:1.2.2")
    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")


    // use for EncryptedSharedPreferences
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Room Local DB

    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:2.6.1")
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

    // work Manager for background tasks
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.hilt:hilt-work:1.2.0")

    //stripe payment
    implementation("com.stripe:stripe-android:20.48.1")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-json:2.3.1")

    // constraint layout compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
}
kapt {
    correctErrorTypes = true
}
