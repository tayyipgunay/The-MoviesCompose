plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")




}

android {
    namespace = "com.tayyipgunay.themoviescompose2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tayyipgunay.themoviescompose2"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Retrofit - API istekleri için

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // JSON dönüşümü için

    // Hilt - Dependency Injection
    implementation("com.google.dagger:hilt-android:2.51.1")

    kapt("com.google.dagger:hilt-compiler:2.51.1") // ✅ SADECE BUNU KULLAN! (Diğerlerini kaldır.)

    // Coroutine - Asenkron işlemler için
    // Coroutines (Asenkron işlemler için)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // Coil - Görsel yükleme için (Compose uyumlu)


    // ViewModel - Compose ile MVVM kullanımı için

    // ViewModel & LiveData (Lifecycle Kütüphaneleri)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")


    // Jetpack Compose Destekleyici Kütüphaneler (Eğer kullanıyorsan)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.2")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")



}
