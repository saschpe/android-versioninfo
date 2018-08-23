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

package com.example.versioninfo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import saschpe.android.versioninfo.widget.VersionInfoDialogFragment;

public final class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showVersionInfoDialog(final View view) {
        VersionInfoDialogFragment
                .newInstance(
                        getString(R.string.app_name),
                        BuildConfig.VERSION_NAME,
                        "Sascha Peilicke",
                        R.mipmap.ic_launcher)
                .show(getSupportFragmentManager(), "version_info");
    }

    public void showVersionInfoFragment(final View view) {
        DialogFragment fragment = VersionInfoDialogFragment.newInstance(
                getString(R.string.app_name),
                BuildConfig.VERSION_NAME,
                "Sascha Peilicke",
                R.mipmap.ic_launcher);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
