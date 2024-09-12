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
    `maven-publish`
    signing
}

dependencies {
    api("androidx.fragment:fragment:1.8.3")
}

android {
    namespace = "saschpe.android.versioninfo"

    defaultConfig {
        compileSdk = 34
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenAndroid") {
            artifactId = "android-versioninfo"
            groupId = "de.peilicke.sascha"
            version = "2.2.0"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Android CustomTabs")
                description.set("A version info widget for Android. Material style.")
                url.set("https://github.com/saschpe/android-customtabs")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("saschpe")
                        name.set("Sascha Peilicke")
                        email.set("sascha@peilicke.de")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/saschpe/android-versioninfo.git")
                    developerConnection.set("scm:git:ssh://github.com/saschpe/android-versioninfo.git")
                    url.set("https://github.com/saschpe/android-versioninfo")
                }
            }
        }
    }

    if (hasProperty("sonatypeUser") && hasProperty("sonatypePass")) {
        repositories {
            maven {
                name = "sonatype"
                credentials {
                    username = property("sonatypeUser") as String
                    password = property("sonatypePass") as String
                }
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenAndroid"])
}
