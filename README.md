# Android VersionInfo
![Maven Central](https://img.shields.io/maven-central/v/de.peilicke.sascha/android-versioninfo)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-VersionInfo-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3832)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/saschpe/android-versioninfo.svg?branch=master)](https://travis-ci.org/saschpe/android-versioninfo)
<a href="http://www.methodscount.com/?lib=saschpe.android%3Aversioninfo%3A2.1.0"><img src="https://img.shields.io/badge/Methods and size-42 | 8 KB-e91e63.svg"/></a>

All about activities have a version info widget somewhere. This library provides one that can be
used as a dialog or fragment. It honors default styling rules, Material-style:

![Dialog](doc/img/versioninfo-sample-dialog.png)
![Fragment](doc/img/versioninfo-sample-fragment.png)


# Usage
Allows to either display version information in a dialog:

```java
VersionInfoDialogFragment.newInstance(
        getString(R.string.app_name),
        BuildConfig.VERSION_NAME,
        "Sascha Peilicke",
        R.mipmap.ic_launcher)
    .show(getSupportFragmentManager(), "version_info");
```

Or a (support) Fragment:

```java
DialogFragment fragment = VersionInfoDialogFragment.newInstance(
        getString(R.string.app_name),
        BuildConfig.VERSION_NAME,
        "Sascha Peilicke",
        R.mipmap.ic_launcher);
getSupportFragmentManager()
    .beginTransaction()
    .replace(R.id.container, fragment)
    .commit();
```

Check out the sample app in `app/` to see it in action.


# Download
Artifacts are published to [Maven Central][maven-central]:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("de.peilicke.sascha:android-versioninfo:2.3.1")
}
```

# In use by
* [Alpha+ Player - Unofficial player for Soma FM](https://play.google.com/store/apps/details?id=saschpe.alphaplus)
* [GameOn - Get games on sale](https://play.google.com/store/apps/details?id=saschpe.gameon)
* [Birthday Calendar](https://play.google.com/store/apps/details?id=saschpe.contactevents) - Open Source on [Github](https://github.com/saschpe/BirthdayCalendar/)
* [Planning Poker - SCRUM Cards](https://play.google.com/store/apps/details?id=saschpe.poker) - Open Source on [Github](https://github.com/saschpe/PlanningPoker)

# License

    Copyright 2016 Sascha Peilicke

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[maven-central]: https://search.maven.org/artifact/de.peilicke.sascha/android-customtabs
