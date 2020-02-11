import com.jfrog.bintray.gradle.BintrayExtension
import groovy.util.Node

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
    id("com.android.library")
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}

repositories {
    google()
    jcenter()
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(28)
        versionName = "2.1.3"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    api("androidx.fragment:fragment:1.2.1")
}

group = "saschpe.android"
version = android.defaultConfig.versionName.toString()

val androidJavadoc by tasks.creating(Javadoc::class) {
    source = android.sourceSets.getByName("main").java.sourceFiles
    classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))

    android.libraryVariants.all { variant ->
        if (variant.name == "release") {
            @Suppress("DEPRECATION")
            classpath += variant.javaCompile.classpath
        }
        true
    }
    exclude("**/R.html", "**/R.*.html", "**index.html")
}

val androidJavadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    from(androidJavadoc.destinationDir)
}
androidJavadocJar.dependsOn(androidJavadoc)

val androidSourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

fun Node.addDependency(dependency: Dependency, scope: String) {
    appendNode("dependency").apply {
        appendNode("groupId", dependency.group)
        appendNode("artifactId", dependency.name)
        appendNode("version", dependency.version)
        appendNode("scope", scope)
    }
}

publishing.publications {
    create("mavenAndroid", MavenPublication::class) {
        groupId = project.group as String
        artifactId = project.name
        version = project.version as String

        afterEvaluate({ artifact(tasks.getByName("bundleReleaseAar")) })
        artifact(androidJavadocJar)
        artifact(androidSourcesJar)

        pom.withXml({
            asNode().appendNode("dependencies").let { dependencies ->
                // List all "api" dependencies as "compile" dependencies
                configurations.api.allDependencies.forEach {
                    dependencies.addDependency(it, "compile")
                }
                // List all "implementation" dependencies as "runtime" dependencies
                configurations.implementation.allDependencies.forEach {
                    dependencies.addDependency(it, "runtime")
                }
            }
        })
    }
}

bintray {
    user = Secrets.Bintray.user
    key = Secrets.Bintray.apiKey
    setPublications("mavenAndroid")
    setConfigurations("archives")
    override = true
    publish = true
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "android-versioninfo"
        userOrg = "saschpe"
        websiteUrl = "https://github.com/saschpe/android-versioninfo"
        issueTrackerUrl = "https://github.com/saschpe/android-versioninfo/issues"
        vcsUrl = "https://github.com/saschpe/android-versioninfo.git"
        desc = "A version info widget for Android. Material style."
        setLabels("aar", "android")
        setLicenses("Apache-2.0")
        publicDownloadNumbers = true

        githubRepo = "saschpe/android-versioninfo"
        githubReleaseNotesFile = "README.md"

        version(delegateClosureOf<BintrayExtension.VersionConfig> {
            name = project.version as String
            desc = "${project.name} ${project.version as String}"
            // released = java.util.Date()
            vcsTag = project.version as String

            gpg(delegateClosureOf<BintrayExtension.GpgConfig> {
                sign = true
            })
        })
    })
}
