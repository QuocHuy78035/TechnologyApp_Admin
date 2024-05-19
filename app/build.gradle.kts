plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.technology_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.technology_app"
        minSdk = 29
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
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(fileTree(mapOf(
        "dir" to "D:\\TranHuuQuocHuy-21110888\\HK6_3\\LTDiDong\\Zalopay",
        "include" to listOf("*.aar", "*.jar"),
        //"exclude" to listOf()
    )))
    implementation(libs.media3.exoplayer)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    //RxJava
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.1.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    //badge
    implementation("com.nex3z:notification-badge:1.0.4")

    //even bus
    implementation("org.greenrobot:eventbus:3.3.1")

    //paper == sharepreference
    implementation("io.github.pilgr:paperdb:2.7.2")

    //gson
    implementation("com.google.code.gson:gson:2.10.1")

    //animation splash
    implementation("com.airbnb.android:lottie:4.2.2")

    //bo tron hinh
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //watch vid on ytb
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    //zalo pay
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")

    //livestream
    //video sdk
    implementation("live.videosdk:rtc-android-sdk:0.1.21")

    // library to perform Network call to generate a meeting id
    implementation("com.amitshekhar.android:android-networking:1.0.2")

    implementation("com.google.android.exoplayer:exoplayer:2.18.5")


}