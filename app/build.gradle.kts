plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"
    defaultConfig {
        applicationId = "com.shafayat.helloworld"
        minSdk = 19
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://raw.githubusercontent.com/shafayathossain/helloworld/master/\"")
            buildConfigField("String", "DB_NAME", "\"app_db\"")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles( getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://raw.githubusercontent.com/shafayathossain/helloworld/master/\"")
            buildConfigField("String", "DB_NAME", "\"app_db\"")
        }
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true

    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("org.mockito:mockito-core:3.10.0")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.mockito:mockito-inline:3.9.0")
    testImplementation("org.powermock:powermock-module-junit4:2.0.9")
    testImplementation("org.powermock:powermock-core:2.0.9")
    testImplementation("org.powermock:powermock-api-mockito2:2.0.9")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("org.robolectric:robolectric:4.5.1")

    // lifecycle component
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    testImplementation("android.arch.core:core-testing:1.1.1")

    // material component
    implementation("com.google.android.material:material:1.6.1")

    // gson
    implementation("com.google.code.gson:gson:2.8.9")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-compiler:2.38.1")

    // rxjava2
    implementation("io.reactivex.rxjava2:rxjava:2.2.17")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // logging
    implementation("com.orhanobut:logger:2.2.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    // room
    implementation("androidx.room:room-runtime:2.4.2")
    kapt ("androidx.room:room-compiler:2.4.2")
    implementation("androidx.room:room-rxjava2:2.4.2")
    testImplementation("androidx.room:room-testing:2.4.2")

    // mockwebserver
    testImplementation("com.squareup.okhttp3:mockwebserver:4.3.1")

    // debug db
    debugImplementation("com.github.amitshekhariitbhu.Android-Debug-Database:debug-db:1.0.6")

    // Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")

}
