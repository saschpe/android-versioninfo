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

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.2.1")
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version "3.16.0"
    id("com.github.ben-manes.versions") version "0.20.0"
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}

spotless {
    format("misc") {
        target("**/*.gradle", "**/*.md", "**/.gitignore")
        trimTrailingWhitespace()
        endWithNewline()
    }
    freshmark {
        propertiesFile("gradle.properties")
    }
    java {
        target("**/*.java")
        trimTrailingWhitespace()
        removeUnusedImports()
    }
    kotlin {
        target("*/src/**/*.kt")
        ktlint("0.29.0")
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint("0.29.0")
    }
}