plugins {
	alias(libs.plugins.com.android.application)
	alias(libs.plugins.org.jetbrains.kotlin.android)
	alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
	alias(libs.plugins.hilt)

	kotlin("kapt")
	id("kotlin-parcelize")
}

android {
	namespace = "com.lampotrias.links"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.lampotrias.links"
		minSdk = 21
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
	kotlinOptions {
		jvmTarget = "1.8"
	}

	buildFeatures {
		viewBinding = true
	}
}

dependencies {

	implementation(libs.core.ktx)
	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.constraintlayout)
	implementation(libs.kotlin.serialization.json)
	implementation(libs.okhttp)
	implementation(libs.coroutines)
	implementation(libs.hilt.android)
	implementation(libs.room.ktx)
	implementation(libs.androidx.workmanager)
	implementation(libs.androidx.hilt.common)
	implementation(libs.androidx.lifecycle)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.fragment)
	implementation(libs.timber)
	implementation(libs.fresco)
	implementation(libs.fresco.okhttp)
	implementation(libs.androidx.junit.ktx)
	implementation(libs.androidx.runner)

	kapt(libs.hilt.compiler)
	kapt(libs.androidx.hilt.compiler)
	//noinspection KaptUsageInsteadOfKsp
	kapt(libs.room.compiler)

	kaptTest(libs.hilt.compiler)

	kaptAndroidTest(libs.hilt.compiler)

	testImplementation(libs.hilt.testing)
	testImplementation(libs.junit)
	testImplementation(libs.google.truth)
	testImplementation(libs.robolectric)

	androidTestImplementation(libs.hilt.testing)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.google.truth)
	androidTestImplementation(libs.espresso.core)
}