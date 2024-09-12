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

dependencies {
    implementation(project(":versioninfo"))
    implementation("androidx.appcompat:appcompat:1.6.1")
}

android {
    namespace = "com.example.versioninfo"

    defaultConfig {
        applicationId = "com.example.versioninfo"
        compileSdk = 34
        minSdk = 21
        targetSdk = 34
        versionCode = 210020200
        versionName = "2.2.0"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug" // Allow installation in parallel to release builds
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}
