/*
 * Copyright 2016 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
}

repositories {
    google()
    mavenCentral()
}

android {
    buildToolsVersion("30.0.3")
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.example.versioninfo"
        minSdkVersion(14)
        targetSdkVersion(30)
        versionCode = 140020200
        versionName = "2.2.0"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug" // Allow installation in parallel to release builds
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":versioninfo"))
    implementation("androidx.appcompat:appcompat:1.2.0")
}
